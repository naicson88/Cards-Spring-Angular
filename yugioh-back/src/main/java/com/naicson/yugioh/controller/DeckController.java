package com.naicson.yugioh.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.service.DeckServiceImpl;

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
	/*
	@GetMapping("/todos")
	public List<Deck> consultar(){
		return deckRepository.findAll();
	}*/
	
	@GetMapping("/por-nome")
	public List<Deck> consultarPorNome(String nomeDeck){
		return deckRepository.findByNomeContaining(nomeDeck);
	}
	
	@GetMapping("/countCards/{deckId}")
	public List<Deck> countNumberOfCards(@PathVariable("deckId") Integer deckId) {
		return  deckService.countNumberOfCards(deckId);
	}
}
