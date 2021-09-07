package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.util.ErrorMessage;

@Service
public interface DeckDetailService {
	
	List<Deck> findByNomeContaining(String nomeDeck);
	
	List<Card> cardsOfDeck(Integer deckId);
	 
	 Optional<Deck> findById(Integer Id) throws ErrorMessage;
}
