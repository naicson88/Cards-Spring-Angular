package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dao.CardDAO;
import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.Sets;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.util.CardSpecification;
import com.naicson.yugioh.util.ErrorMessage;

@Service
public class CardServiceImpl implements CardDetailService {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	CardDAO dao;
	
	//Trazer o card para mostrar os detalhes;
	public Card cardDetails(Integer id) {
		Query query = em.createNativeQuery("SELECT * FROM TAB_CARDS WHERE ID = :deckId", Card.class);
		Card card = (Card) query.setParameter("deckId", id).getSingleResult();
		return card;
	}	
	
	public List<Deck> cardDecks(Integer cardNumero) {
		Query query = em.createNativeQuery("SELECT *\r\n" + 
				" FROM tab_decks deck \r\n" + 
				"inner join TAB_REL_DECK_CARDS decks on deck.id = decks.deck_id\r\n" + 
				"where decks.card_numero = :cardNumero", Deck.class);
		
		List<Deck> decks_set = (List<Deck>) query.setParameter("cardNumero", cardNumero).getResultList();
		return decks_set;
	}
	
	@Override
	public List<RelUserCardsDTO> searchForCardsUserHave(int[] cardsNumbers) throws SQLException, ErrorMessage {
		try {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		
		if(user.getId() == 0) {
			throw new ErrorMessage("Unable to query user cards, user ID not entered");
		}
		
		if(cardsNumbers == null || cardsNumbers.length == 0) {
			throw new ErrorMessage("Unable to query user cards, decks IDs not entered");
		}
		
	     String cardsNumbersString = "";
	     
	     for(int id: cardsNumbers) {
	    	 cardsNumbersString += id;
	    	 cardsNumbersString += ",";
	     }	     
	     cardsNumbersString += "0";
	     
	     List<RelUserCardsDTO> relUserCardsList = dao.searchForCardsUserHave(user.getId(), cardsNumbersString);
		
	     return relUserCardsList;
	     
		}catch(ErrorMessage em) {
			throw em;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<Card> listar() {
		// TODO Auto-generated method stub
		return cardRepository.findAll();
	}

	@Override
	public Card listarId(int id) {
		// TODO Auto-generated method stub
		return cardRepository.findById(id);
	}

	@Override
	public Card add(Card card) {

		return cardRepository.save(card);
	}

	@Override
	public Card editar(Card card) {
		// TODO Auto-generated method stub
		return cardRepository.save(card);
	}

	@Override
	public Card deletar(int id) {
		// TODO Auto-generated method stub
		Card card = cardRepository.findById(id);
		if(card != null) {
			cardRepository.delete(card);
		}
		
		return card;
	}

	@Override
	public Card encontrarPorNumero(Integer numero) {
		return cardRepository.findByNumero(numero);
	}

	@Override
	public Card listarNumero(Integer numero) {
		return cardRepository.findByNumero(numero);
	}

	@Override
	public List<Card> encontrarPorArchetype(int archId) {
		return cardRepository.findByArchetype(archId);
	}
	
	

	
}
