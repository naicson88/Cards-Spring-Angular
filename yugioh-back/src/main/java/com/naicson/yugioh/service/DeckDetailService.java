package com.naicson.yugioh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Deck;

@Service
public interface DeckDetailService {
	
	public List<Deck> countNumberOfCards(Integer deckId);
	List<Deck> findByNomeContaining(String nomeDeck);
	
}
