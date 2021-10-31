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
	
	List<Card> cardsOfDeck(Long deckId) throws ErrorMessage;
	 
	Deck findById(Long Id) throws Exception;

	List<RelDeckCards> relDeckCards(Long deckId);

	int addSetToUserCollection(Long originalDeckId) throws SQLException, ErrorMessage, Exception;

	int ImanegerCardsToUserCollection(Long originalDeckId, String flagAddOrRemove) throws SQLException, ErrorMessage;

	List<RelUserDeckDTO> searchForDecksUserHave(Long[] decksIds) throws SQLException, ErrorMessage;

	Long addDeck(Deck deck) throws SQLException, ErrorMessage;

	int addCardsToUserDeck(Long originalDeckId, Long generatedDeckId) throws SQLException, Exception, ErrorMessage;

	int removeSetFromUsersCollection(Long setId) throws SQLException, ErrorMessage, Exception;
	
	int addOrRemoveCardsToUserCollection(Long originalDeckId, int userId, String flagAddOrRemove)
			throws SQLException, ErrorMessage;
	
	
}
