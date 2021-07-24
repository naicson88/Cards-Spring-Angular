package com.naicson.yugioh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.Sets;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.service.CardDetailService;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.util.CardSpecification;
import com.naicson.yugioh.util.CardSpecificationBuilder;
import com.naicson.yugioh.util.SearchCriteria;


@RestController
@RequestMapping({"yugiohAPI/cards"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CardController {
	
	@Autowired
	CardDetailService cardService;
	@Autowired
	DeckServiceImpl deckService;
	@Autowired
	CardRepository cardRepository;
	
	@GetMapping
	public List<Card> listar(){
		return cardService.listar();
	}
	
	@PostMapping
	public Card adicionar(@RequestBody Card card) {
		return cardService.add(card);
	}
	
	@GetMapping(path = {"id/{id}"})
	public Card listarId(@PathVariable("id") Integer id) {	
		return cardService.listarId(id);
	}
	
	@GetMapping(path = {"num/{numero}"})
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
	
	@PutMapping(path = {"editar/{id}"})
	public Card editar(@RequestBody Card card, @PathVariable("id") Integer id) {
		card.setId(id);
		return cardService.editar(card);
	}
	
	@DeleteMapping(path = {"del/{id}"})
	public Card card (@PathVariable("id") int id) {
		return cardService.deletar(id);
	}	
	
	@PostMapping(path = {"/searchCard"})
	@ResponseBody
	public List<Card> search(@RequestBody List<SearchCriteria> criterias){
		CardSpecification spec = new CardSpecification();
		
		for(SearchCriteria criterio: criterias) {
			spec.add(new SearchCriteria(criterio.getKey(), criterio.getOperation(), criterio.getValue()));
		}
		
		List<Card> list = cardRepository.findAll(spec);
		
		return list;
	}
	
	/*
	 * @GetMapping(path = {"/teste"}) public void testeSpec() { CardSpecification
	 * spec = new CardSpecification(new SearchCriteria("nome", ":",
	 * "Dark Magician"));
	 * 
	 * List<Card> results = cardRepository.findAll(spec);
	 * 
	 * System.out.println(results.get(0).toString()); }
	 */
	
	/*
	 * @GetMapping(path = {"/testeBuild"})
	 * 
	 * @ResponseBody public List<Card> search(@RequestParam(value = "search") String
	 * search){ CardSpecificationBuilder builder = new CardSpecificationBuilder();
	 * Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|;)(\\w+?),",
	 * Pattern.CASE_INSENSITIVE); Matcher matcher = pattern.matcher(search + ",");
	 * 
	 * List<String> list = new ArrayList<>(); while(matcher.find()) {
	 * 
	 * if(matcher.group(2).equals(";")) { String[] separado =
	 * matcher.group(3).split("_");
	 * 
	 * for(int i = 0; i < separado.length; i++ ) { list.add(separado[i]); } }
	 * 
	 * 
	 * if(list == null) builder.with(matcher.group(1), matcher.group(2),
	 * matcher.group(3)); else builder.with(matcher.group(1), matcher.group(2),
	 * list); }
	 * 
	 * Specification<Card> spec = builder.build(); return
	 * cardRepository.findAll(spec); }
	 */
	
}
