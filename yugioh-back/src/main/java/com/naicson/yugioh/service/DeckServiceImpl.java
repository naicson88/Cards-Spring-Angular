package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.ErrorManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naicson.yugioh.dao.DeckDAO;
import com.naicson.yugioh.dto.DeckDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.User;

import Util.ErrorMessage;

@Service
public class DeckServiceImpl implements DeckDetailService {

	// @Autowired
	@PersistenceContext
	EntityManager em;

	@Autowired
	DeckDAO dao;

	public Deck deck(Integer deckId) {
		Query query = em.createNativeQuery("SELECT * FROM TAB_DECKS WHERE ID = :deckId", Deck.class);
		Deck deck = (Deck) query.setParameter("deckId", deckId).getSingleResult();
		return deck;
	}

	// Traz informações completas dos cards contidos num deck
	@Transactional
	public List<Card> cardsOfDeck(Integer deckId) {
		Query query = em.createNativeQuery("SELECT * FROM TAB_CARDS WHERE NUMERO IN\r\n"
				+ "(SELECT CARD_NUMERO FROM tab_rel_deck_cards WHERE DECK_ID = :deckId)\r\n" + "order by case\r\n"
				+ "when categoria LIKE 'link monster' then 1\r\n" + "when categoria like 'XYZ Monster' then 2\r\n"
				+ "when categoria like 'Fusion Monster' then 3\r\n" + "when categoria like '%Synchro%' then 4\r\n"
				+ "when categoria LIKE '%monster%' then 5\r\n" + "when categoria = 'Spell Card' then 6\r\n"
				+ "ELSE    7\r\n" + "END", Card.class);
		List<Card> cards = (List<Card>) query.setParameter("deckId", deckId).getResultList();
		return cards;
	}

	// Traz informações da relação entre o deck e os cards
	@Transactional
	public List<RelDeckCards> relDeckAndCards(Integer deck_id) {
		Query query = em.createNativeQuery(" select * from tab_rel_deck_cards where deck_id= :deck_id",
				RelDeckCards.class);
		List<RelDeckCards> rel = (List<RelDeckCards>) query.setParameter("deck_id", deck_id).getResultList();

		return rel;
	}

	// Preenche o deck apenas com a relação de card que contenha esse card number e
	// mostra na tela de detalhes do Card
	@Transactional
	public List<RelDeckCards> relDeckAndCards(Integer deck_id, Integer card_number) {
		Query query = em.createNativeQuery(
				" select * from tab_rel_deck_cards where deck_id= :deck_id AND card_numero = :card_number",
				RelDeckCards.class);
		List<RelDeckCards> rel = (List<RelDeckCards>) query.setParameter("deck_id", deck_id)
				.setParameter("card_number", card_number).getResultList();

		return rel;
	}

	@Override
	@Transactional
	public int InsertOnSets(Integer deck_id, Integer card_numero, String card_raridade, String card_set_code,
			String card_price) throws SQLException {

		Query query = em.createNativeQuery(
				"INSERT INTO tab_rel_deck_cards VALUES (:deck_id,:card_numero,:card_raridade,:card_set_code,:card_price)")
				.setParameter("deck_id", deck_id).setParameter("card_numero", card_numero)
				.setParameter("card_raridade", card_raridade).setParameter("card_set_code", card_set_code)
				.setParameter("card_price", Double.parseDouble(card_price));

		System.out.println(query);

		return query.executeUpdate();
	}

	@Override
	public List<Deck> findByNomeContaining(String nomeDeck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public int manegerCardsToUserCollection(Integer deckId, String flagAddOrRemove) throws SQLException, ErrorMessage {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			int itemAtualizado;

			Integer alreadyHasThisDeck = dao.verifyIfUserAleadyHasTheDeck(deckId, user.getId());

			// Se o usuário não tiver o Deck e for passado parametro para remover esse deck.
			if (alreadyHasThisDeck != null && alreadyHasThisDeck == 0 && flagAddOrRemove.equals("R")) {
				return 0;
			}

			if (alreadyHasThisDeck != null && alreadyHasThisDeck == 0) {
				itemAtualizado = dao.addDeckToUserCollection(deckId, user.getId());

				if (itemAtualizado < 1) {
					throw new ErrorMessage("Unable to include Deck for User!");
				}

			} else {
				itemAtualizado = dao.changeQuantitySpecificDeckUserHas(deckId, user.getId(), flagAddOrRemove);

				if (itemAtualizado < 1) {
					throw new ErrorMessage("Unable to manege Deck for User!");
				}
			}

			int qtdAddedOrRemoved = this.addOrRemoveCardsToUserCollection(deckId, user.getId(), flagAddOrRemove);

			if (qtdAddedOrRemoved < 1) {
				throw new ErrorMessage("Unable to include Cards for User!");
			}

			return qtdAddedOrRemoved;

		} catch (ErrorMessage msg) {
			throw msg;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	private int addOrRemoveCardsToUserCollection(Integer deckId, int userId, String flagAddOrRemove)
			throws SQLException, ErrorMessage {
		try {
			int qtdCardsAddedOrRemoved = 0;

			List<DeckDTO> relDeckAndCards = dao.relationDeckAndCards(deckId);

			if (relDeckAndCards != null && relDeckAndCards.size() > 0) {
				if (flagAddOrRemove.equals("A") || flagAddOrRemove.equals("R")) {

					for (DeckDTO relation : relDeckAndCards) {
						// Verifica se o usuário ja possui essa carta.
						boolean alreadyHasThisCard = dao.verifyIfUserAleadyHasTheCard(userId,
								relation.getCard_set_code());

						if (alreadyHasThisCard == true) {
							// Remove ou adiciona a qtd desta carta de acordo com a flag passada.
							int manegeQtd = dao.changeQuantityOfEspecifCardUserHas(userId, relation.getCard_set_code(),
									flagAddOrRemove);

							if (manegeQtd < 1) {
								throw new ErrorMessage("It was not possible to manege card to the user's collection!");
							}

							qtdCardsAddedOrRemoved++;

						} else {
							// Caso o usuário não tenha o Card, simplesmente da um insert desse card na
							// coleção do usuário.
							int insertCard = dao.insertCardToUserCollection(userId, relation.getCard_set_code(),
									relation.getCard_numero());

							if (insertCard < 1) {
								throw new ErrorMessage(
										"It was not possible to add this Card to the user's collection.");
							}

							qtdCardsAddedOrRemoved++;
						}
					}

				} else {
					throw new ErrorMessage("Check the Add or Remove parameter sent!");
				}

			} else {
				throw new ErrorMessage("No Cards related to this Deck were found!");
			}

			return qtdCardsAddedOrRemoved;

		} catch (ErrorMessage msg) {
			throw msg;
		} catch (Exception ex) {
			throw ex;
		}
	}

}
