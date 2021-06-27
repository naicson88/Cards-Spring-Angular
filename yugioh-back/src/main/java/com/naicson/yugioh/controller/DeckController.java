package com.naicson.yugioh.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.service.DeckServiceImpl;

import Util.ErrorMessage;

@RestController
@RequestMapping({"yugiohAPI/decks"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class DeckController {
	
	@Autowired
	DeckRepository deckRepository;
	@Autowired
	DeckServiceImpl deckService;
	
	
	@GetMapping("/todos")
	public List<Deck> consultar(){
		return deckRepository.findAll();
	}
	
	
	@GetMapping(path = {"/{id}"})
	public Deck deckAndCards(@PathVariable("id") Integer id) {
		List<Card> cardList = deckService.cardsOfDeck(id);
		List <RelDeckCards> rel_deck_cards = deckService.relDeckAndCards(id);
		Deck deck = deckService.deck(id);
		deck.setCards(cardList);
		deck.setRel_deck_cards(rel_deck_cards);
		
		return deck;
		//return deckRepository.findById(id);
	}
	
	@GetMapping("/por-nome")
	public List<Deck> consultarPorNome(String nomeDeck){
		return deckRepository.findByNomeContaining(nomeDeck);
	}
	
	@GetMapping(path = {"/add-deck-to-user-collection/{deckId}/{flagAddOrRemove}"})
	public int addDeckToUserCollection(@PathVariable("deckId") Integer deckId, @PathVariable("flagAddOrRemove") String flagAddOrRemove) throws SQLException, ErrorMessage {	
	return deckService.manegerCardsToUserCollection(deckId, flagAddOrRemove );

	}
	

}
