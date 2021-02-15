package com.naicson.yugioh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;

@Service
public interface DeckDetailService {
	
	List<Deck> findByNomeContaining(String nomeDeck);
	
	Deck deck(Long deckId);
	List<Card> cardsOfDeck(Long deckId);
}
