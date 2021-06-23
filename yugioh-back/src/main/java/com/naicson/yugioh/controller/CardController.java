package com.naicson.yugioh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.Sets;
import com.naicson.yugioh.service.CardDetailService;
import com.naicson.yugioh.service.DeckServiceImpl;


@RestController
@RequestMapping({"yugiohAPI/cards"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CardController {
	
	@Autowired
	CardDetailService cardService;
	@Autowired
	DeckServiceImpl deckService;
	
	@GetMapping
	public List<Card> listar(){
		return cardService.listar();
	}
	
	@PostMapping
	public Card adicionar(@RequestBody Card card) {
		return cardService.add(card);
	}
	
	@GetMapping(path = {"/{id}"})
	public Card listarId(@PathVariable("id") Integer id) {	
		return cardService.listarId(id);
	}
	
	@GetMapping(path = {"/{numero}"})
	public Card listarNumero(@PathVariable("numero") Integer numero) {
		return cardService.listarNumero(numero);
	}
	
	@GetMapping(path = {"number/{cardNumero}"})
	public Card procuraPorCardNumero(@PathVariable("cardNumero") Integer cardNumero) {
		List<Deck> deck_set = cardService.cardDecks(cardNumero);
		
		Card card = cardService.encontrarPorNumero(cardNumero);
		card.setSet_decks(deck_set);
		
		for(Deck rel : deck_set) {
			List<RelDeckCards> rels = deckService.relDeckAndCards(rel.getId(), cardNumero);
			rel.setRel_deck_cards(rels);
		}
		
		return card;
		
	}
	
	@PutMapping(path = {"/{id}"})
	public Card editar(@RequestBody Card card, @PathVariable("id") Integer id) {
		card.setId(id);
		return cardService.editar(card);
	}
	
	@DeleteMapping(path = {"/{id}"})
	public Card card (@PathVariable("id") int id) {
		return cardService.deletar(id);
	}	
	
	/*@GetMapping(path = "/{id}")
	public Card cardDetails(@PathVariable("id") Integer id) {
		return cardService.cardDetails(id);
	}*/
	
}
