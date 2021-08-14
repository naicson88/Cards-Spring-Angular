package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naicson.yugioh.dao.DeckDAO;
import com.naicson.yugioh.dto.DeckDTO;
import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.util.ErrorMessage;

@Service
public class DeckServiceImpl implements DeckDetailService {


	@PersistenceContext
	EntityManager em;
	
	@Autowired
	DeckDAO dao;
	
	@Autowired
	DeckRepository deckRepository;

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

	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int addSetToUserCollection(Integer originalDeckId) throws SQLException, ErrorMessage, Exception {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

			// Consulta o deck pelo Id
			Optional<Deck> dk = deckRepository.findById(originalDeckId);
			
			if(dk == null) {
				throw new ErrorMessage("No Set found with this code.");
			}
			
			Deck deckOrigem = dk.get();

			if (user != null) {
				
				// Cria um novo deck que será inserido como do usuário
				Deck newDeck = new Deck();
				newDeck.setImagem(deckOrigem.getImagem());
				newDeck.setNome(deckOrigem.getNome());
				newDeck.setNomePortugues(deckOrigem.getNomePortugues());
				newDeck.setSetType("UD");
				newDeck.setQtd_cards(deckOrigem.getQtd_cards());
				newDeck.setQtd_comuns(deckOrigem.getQtd_comuns());
				newDeck.setQtd_raras(deckOrigem.getQtd_raras());
				newDeck.setQtd_secret_raras(deckOrigem.getQtd_secret_raras());
				newDeck.setQtd_super_raras(deckOrigem.getQtd_super_raras());
				newDeck.setQtd_ulta_raras(deckOrigem.getQtd_ulta_raras());
				newDeck.setIsKonamiDeck("N");
				newDeck.setDt_criacao(new Date());
				newDeck.setCopiedFromDeck(deckOrigem.getId());
				if(newDeck.getCopiedFromDeck() == null) {
					throw new ErrorMessage("Erro null");
				}
				newDeck.setUserId(user.getId());				
				
				if (newDeck != null) {
					
					Integer generatedDeckId = dao.addDeck(newDeck);

					if (generatedDeckId == null || generatedDeckId == 0) {
						throw new ErrorMessage("It was not possible add Deck to user.");
					}
					
					//Adiciona os cards do Deck original ao novo Deck.
					int addCardsOnNewDeck = this.addCardsToDeck(originalDeckId, generatedDeckId);
					
					if(addCardsOnNewDeck <= 0) {
						throw new ErrorMessage("It was not possible add cards to the new Deck.");
					}
					
					//Adiciona os cards a coleção do usuário.
					 int addCardsToUsersCollection = this.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(), "A");

					if (addCardsToUsersCollection < 1) {
						throw new ErrorMessage("Unable to include Cards for User!");
					}

					return addCardsToUsersCollection;

				} else {
					throw new ErrorMessage("It was not possible find de Deck!");
				}
			} else {
				throw new ErrorMessage("It was not able found user!");
			}

		} catch (ErrorMessage msg) {
			throw msg;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int ImanegerCardsToUserCollection(Integer originalDeckId, String flagAddOrRemove)
			throws SQLException, ErrorMessage {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			int itemAtualizado;

			Integer alreadyHasThisDeck = dao.verifyIfUserAleadyHasTheDeck(originalDeckId, user.getId());

			// Se o usuário não tiver o Deck e for passado parametro para remover esse deck.
			if (alreadyHasThisDeck != null && alreadyHasThisDeck == 0 && flagAddOrRemove.equals("R")) {
				return 0;
			}

			if (alreadyHasThisDeck != null && alreadyHasThisDeck == 0) {
				itemAtualizado = dao.addDeckToUserCollection(originalDeckId, user.getId());

				if (itemAtualizado < 1) {
					throw new ErrorMessage("Unable to include Deck for User!");
				}

			} else {
				itemAtualizado = dao.changeQuantitySpecificDeckUserHas(originalDeckId, user.getId(), flagAddOrRemove);

				if (itemAtualizado < 1) {
					throw new ErrorMessage("Unable to manege Deck for User!");
				}
			}

			int qtdAddedOrRemoved = this.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(),
					flagAddOrRemove);

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

	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
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
							RelUserCardsDTO rel = new RelUserCardsDTO();
							rel.setUserId(userId);
							rel.setCardNumero(relation.getCard_numero());
							rel.setCardSetCode(relation.getCard_set_code());
							rel.setQtd(1);
							rel.setDtCriacao(new Date());
							
							int insertCard = dao.insertCardToUserCollection(rel);

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

			} 

			return qtdCardsAddedOrRemoved;

		} catch (ErrorMessage msg) {
			throw msg;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<RelUserDeckDTO> searchForDecksUserHave(int[] decksIds) throws SQLException, ErrorMessage {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

			if (user.getId() == 0) {
				throw new ErrorMessage("Unable to query user decks, user ID not entered");
			}

			if (decksIds == null || decksIds.length == 0) {
				throw new ErrorMessage("Unable to query user decks, decks IDs not entered");
			}

			String decksIdsString = "";

			for (int id : decksIds) {
				decksIdsString += id;
				decksIdsString += ",";
			}
			decksIdsString += "0";

			List<RelUserDeckDTO> relUserDeckList = dao.searchForDecksUserHave(user.getId(), decksIdsString);

			return relUserDeckList;

		} catch (ErrorMessage em) {
			throw em;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int addDeck(Deck deck) throws SQLException, ErrorMessage {
		int id = 0;

		if (deck != null) {
			dao = new DeckDAO();
			id = dao.addDeck(deck);
		} else {
			throw new ErrorMessage("O deck informado não é válido.");
		}

		return id;
	}
	
	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int addCardsToDeck(Integer originalDeckId, Integer generatedDeckId) throws SQLException, Exception, ErrorMessage {
		try {			
			if (originalDeckId == null && generatedDeckId == null) {
				throw new ErrorMessage("Original deck or generated deck is null");
			}			
			int cardsAddedToDeck = dao.addCardsToDeck(generatedDeckId, originalDeckId);
			
			return cardsAddedToDeck;
			
		}catch(ErrorMessage em) {
			throw em;
		}
	}

	public int removeSetFromUsersCollection(Integer setId) throws SQLException, ErrorMessage, Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

		
		// Consulta o deck pelo Id
		Optional<Deck> dk = deckRepository.findById(setId);
		
		if(dk.isEmpty()) {
			throw new ErrorMessage("No Set found with this code.");
		}
		
		Deck setOrigem = dk.get();
		//Não pode remover um Set que é da Konami.
		if(setOrigem.getIsKonamiDeck().equals("S")) {
			throw new ErrorMessage("Konami Set cannot be deleted!");
		}
		
		//Remove os cards da coleção do usuário. 
		int qtdRemoved = this.addOrRemoveCardsToUserCollection(setId, user.getId(), "R");
		
		//Remove os Cards do Set.
		dao.removeCardsFromSet(setId);
		
		//Remove o Set.
		deckRepository.deleteById(setOrigem.getId());
		
		return qtdRemoved;
		
	}
}
