package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.QueryHint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;

@Service
public class DeckServiceImpl implements DeckDetailService {
	
	@Autowired
	 EntityManager em;
	
	public Deck deck (Long deckId) {
		Query query = em.createNativeQuery("SELECT * FROM TAB_DECKS WHERE ID = :deckId", Deck.class);			
				Deck deck = (Deck)  query.setParameter("deckId", deckId).getSingleResult();
				return deck;
	}
	
	//Traz informações completas dos cards contidos num deck
	public List<Card> cardsOfDeck(Long deckId){	
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
	
	//Traz informações darelação entre o deck e os cards
	public List<RelDeckCards> relDeckAndCards(Long deck_id) {	
		Query query = em.createNativeQuery(" select * from tab_rel_deck_cards where deck_id= :deck_id",
				RelDeckCards.class);
		List<RelDeckCards> rel = (List<RelDeckCards>) query.setParameter("deck_id", deck_id).getResultList();
		
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

}
