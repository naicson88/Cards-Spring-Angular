package com.naicson.yugioh.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.cards.CardAndSetsDTO;
import com.naicson.yugioh.dto.cards.CardOfArchetypeDTO;
import com.naicson.yugioh.dto.cards.CardOfUserDetailDTO;
import com.naicson.yugioh.dto.cards.CardsSearchDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.util.exceptions.ErrorMessage;
import com.naicson.yugioh.util.search.CardSpecification;
import com.naicson.yugioh.util.search.SearchCriteria;

@Service
public interface CardDetailService {
	
	List<Card> listar();
	
	Card listarId(int id);
	
	Card listarNumero(Long numero);
	
	Card add(Card card);
	
	Card editar(Card card);
	
	Card deletar (int id);
	
	Card cardDetails(Integer id);
	
	List<Deck> cardDecks(Long cardNumero);	
	
	List<CardOfArchetypeDTO> encontrarPorArchetype(Integer archId);
	
	List<RelUserCardsDTO> searchForCardsUserHave(int[] cardsNumbers) throws SQLException, ErrorMessage;
	
	CardAndSetsDTO findCardToAddToUserCollection(Long cardNumber) throws SQLException, ErrorMessage;
	
	CardOfUserDetailDTO cardOfUserDetails(Long cardNumber) throws ErrorMessage, SQLException, Exception;
	
	Card findCardByNumberWithDecks(Long cardNumero);
	
	List<CardsSearchDTO> getByGenericType(Pageable page, String getByGenericType, long userId);
	
	Page<Card> findAll(CardSpecification spec,  Pageable pageable);

	List<CardsSearchDTO> cardSearch(List<SearchCriteria> criterias, String join, Pageable pageable);

	List<CardsSearchDTO> cardSearchByNameUserCollection(String cardName, Pageable pageable);

	List<Card> randomCardsDetailed();

	Page<Card> searchCardDetailed(List<SearchCriteria> criterias, String join, Pageable pageable);

	List<RelDeckCards> findAllRelDeckCardsByCardNumber(Long cardNumber);
	
	List<Long> findCardsNotRegistered(List<Long> cardsNumber);

}
