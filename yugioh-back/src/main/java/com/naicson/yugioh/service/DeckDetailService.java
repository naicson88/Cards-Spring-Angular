package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;

@Service
public interface DeckDetailService {
	
	List<Deck> findByNomeContaining(String nomeDeck);
	
	Deck deck(Integer deckId);
	List<Card> cardsOfDeck(Integer deckId);

	 int InsertOnSets(Integer deck_id, Integer card_numero, String card_raridade, String card_set_code,
			String card_price) throws SQLException ;
}
