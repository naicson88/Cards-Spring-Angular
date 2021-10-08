package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.util.ErrorMessage;

@Service
public interface DeckDetailService {
	
	List<Deck> findByNomeContaining(String nomeDeck);
	
	List<Card> cardsOfDeck(Integer deckId);
	 
	 Optional<Deck> findById(Integer Id);

	List<RelDeckCards> relDeckCards(Integer deckId);

	int addSetToUserCollection(Integer originalDeckId) throws SQLException, ErrorMessage, Exception;

	int ImanegerCardsToUserCollection(Integer originalDeckId, String flagAddOrRemove) throws SQLException, ErrorMessage;

	List<RelUserDeckDTO> searchForDecksUserHave(int[] decksIds) throws SQLException, ErrorMessage;

	int addDeck(Deck deck) throws SQLException, ErrorMessage;

	int addCardsToUserDeck(Integer originalDeckId, Long generatedDeckId) throws SQLException, Exception, ErrorMessage;

	int removeSetFromUsersCollection(Integer setId) throws SQLException, ErrorMessage, Exception;
}
