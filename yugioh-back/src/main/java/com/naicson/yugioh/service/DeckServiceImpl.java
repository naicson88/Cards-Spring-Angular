package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naicson.yugioh.dao.DeckDAO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.User;

@Service
public class DeckServiceImpl implements DeckDetailService {
	
	//@Autowired
	@PersistenceContext
	 EntityManager em;
	
	@Autowired
	DeckDAO dao;
	
	public Deck deck (Integer deckId) {
		Query query = em.createNativeQuery("SELECT * FROM TAB_DECKS WHERE ID = :deckId", Deck.class);			
				Deck deck = (Deck)  query.setParameter("deckId", deckId).getSingleResult();
				return deck;
	}
	
	//Traz informações completas dos cards contidos num deck
	@Transactional
	public List<Card> cardsOfDeck(Integer deckId){	
		Query query = em.createNativeQuery("SELECT * FROM TAB_CARDS WHERE NUMERO IN\r\n" + 
				"(SELECT CARD_NUMERO FROM tab_rel_deck_cards WHERE DECK_ID = :deckId)\r\n" + 
				"order by case\r\n" + 
				"when categoria LIKE 'link monster' then 1\r\n" + 
				"when categoria like 'XYZ Monster' then 2\r\n" + 
				"when categoria like 'Fusion Monster' then 3\r\n" + 
				"when categoria like '%Synchro%' then 4\r\n" + 
				"when categoria LIKE '%monster%' then 5\r\n" + 
				"when categoria = 'Spell Card' then 6\r\n" + 
				"ELSE    7\r\n" + 
				"END", Card.class);			
				List<Card> cards = (List<Card>) query.setParameter("deckId", deckId).getResultList();
				return cards;			
	}
	
	//Traz informações da relação entre o deck e os cards
	@Transactional
	public List<RelDeckCards> relDeckAndCards(Integer deck_id) {	
		Query query = em.createNativeQuery(" select * from tab_rel_deck_cards where deck_id= :deck_id",
				RelDeckCards.class);
		List<RelDeckCards> rel = (List<RelDeckCards>) query.setParameter("deck_id", deck_id).getResultList();
		
		return rel;
	}
	
	//Preenche o deck apenas com a relação de card que contenha esse card number e mostra na tela de detalhes do Card
	@Transactional
	public List<RelDeckCards> relDeckAndCards(Integer deck_id, Integer card_number) {	
		Query query = em.createNativeQuery(" select * from tab_rel_deck_cards where deck_id= :deck_id AND card_numero = :card_number",
				RelDeckCards.class);
		List<RelDeckCards> rel = (List<RelDeckCards>) query.setParameter("deck_id", deck_id).setParameter("card_number", card_number).getResultList();
		
		return rel;
	}
		
	@Override
	@Transactional
	public int InsertOnSets(Integer deck_id, Integer card_numero, String card_raridade, String card_set_code,
			String card_price) throws SQLException {
	
		Query query = em.createNativeQuery("INSERT INTO tab_rel_deck_cards VALUES (:deck_id,:card_numero,:card_raridade,:card_set_code,:card_price)")
				.setParameter("deck_id", deck_id)
				.setParameter("card_numero", card_numero)
				.setParameter("card_raridade", card_raridade)
				.setParameter("card_set_code", card_set_code)
				.setParameter("card_price",  Double.parseDouble(card_price));
		
		System.out.println(query);
						
		return query.executeUpdate();
	}

	@Override
	public List<Deck> findByNomeContaining(String nomeDeck) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Transactional
	public int addDeckToUserCollection(Integer deckId) throws SQLException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		int idGerado = dao.addDeckToUserCollection(deckId, user.getId());
		
		return idGerado;
		/*
		 * Query query = em.
		 * createNativeQuery("INSERT INTO TAB_REL_USER_DECK (user_id, deck_id) values(:deck_id, :user_id)"
		 * ) .setParameter("deck_id", deckId) .setParameter("user_id", user.getId());
		 * 
		 * return query.executeUpdate();
		 */
	
	}

	/*
	 * @Transactional public List<Card> addCardsToUserCollection(Long deckId, Long
	 * userId) throws SQLException { Query query = em.
	 * createNativeQuery("INSERT INTO TAB_REL_USER_CARDS values (:card_numero, :user_id)"
	 * ) .setParameter("card_numero", value) }
	 */

}
