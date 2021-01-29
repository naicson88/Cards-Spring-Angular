package com.naicson.yugioh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.repository.DeckRepository;

@RestController
@RequestMapping({"yugiohAPI/decks"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class DeckController {
	
	@Autowired
	DeckRepository deckRepository;
	
	@GetMapping("/todos")
	public List<Deck> consultar(){
		return deckRepository.findAll();
	}
	
	@GetMapping("/por-nome")
	public List<Deck> consultarPorNome(String nomeDeck){
		return deckRepository.findByNomeContaining(nomeDeck);
	}
}
