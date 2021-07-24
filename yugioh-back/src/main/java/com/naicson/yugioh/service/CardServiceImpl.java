package com.naicson.yugioh.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.Sets;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.util.CardSpecification;

@Service
public class CardServiceImpl implements CardDetailService {
	
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	EntityManager em;
	
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
