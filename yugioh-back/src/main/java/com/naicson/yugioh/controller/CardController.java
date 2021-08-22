package com.naicson.yugioh.controller;

import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.dto.cards.CardAndSetsDTO;
import com.naicson.yugioh.dto.cards.CardsSearchDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.Sets;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.service.CardDetailService;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.util.CardSpecification;
import com.naicson.yugioh.util.CardSpecificationBuilder;
import com.naicson.yugioh.util.ErrorMessage;
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
	
	//Transforma as cartas encontradas no DTO para passar menos parametros
	@PostMapping(path = {"/searchCard"})
	@ResponseBody
	public ResponseEntity<List<CardsSearchDTO>> cardSearch(@RequestBody List<SearchCriteria> criterias){
		List<CardsSearchDTO> dtoList = new ArrayList<>();
		
		CardSpecification spec = new CardSpecification();	
		for(SearchCriteria criterio: criterias) {
			spec.add(new SearchCriteria(criterio.getKey(), criterio.getOperation(), criterio.getValue()));
		}
		
		List<Card> list = cardRepository.findAll(spec);
		
		for(Card card : list) {
			if(list != null && list.size() > 0) 
				dtoList.add(CardsSearchDTO.transformInDTO(card));
		}
		
		return new ResponseEntity<List<CardsSearchDTO>>(dtoList, HttpStatus.OK);
	}
	
	@GetMapping(path = {"/randomCards"})
	@ResponseBody
	public ResponseEntity<List<CardsSearchDTO>> randomCards(){
		List<CardsSearchDTO> dtoList = new ArrayList<>();
		
		List<Card> list = cardRepository.findRandomCards();
		
		for(Card card : list) {
			if(list != null && list.size() > 0) 
				dtoList.add(CardsSearchDTO.transformInDTO(card));
		}
		
		return new ResponseEntity<List<CardsSearchDTO>>(dtoList, HttpStatus.OK);
	}
	
	@GetMapping(path = {"/rel-user-cards"})
	@ResponseBody
	public ResponseEntity<List<RelUserCardsDTO>> searchForCardsUserHave(@RequestParam int[] cardsNumbers) throws SQLException, ErrorMessage {
		List<RelUserCardsDTO> rel = null;
		
		if(cardsNumbers != null && cardsNumbers.length > 0) {
			rel = cardService.searchForCardsUserHave(cardsNumbers);
		}
		
		if(rel != null && rel.size() > 0) {
			return new ResponseEntity<List<RelUserCardsDTO>>(rel, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<RelUserCardsDTO>>(rel, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(path = {"/add-card-to-user"})
	@ResponseBody
	public ResponseEntity<CardAndSetsDTO> findCardToAddToUserCollection(@RequestParam Long cardNumber) throws SQLException, ErrorMessage {
		
		if(cardNumber <= 0)
			throw new ErrorMessage(" The card number is invalid!");
		
		CardAndSetsDTO dto = cardService.findCardToAddToUserCollection(cardNumber);
		
		if(dto != null ) {
			return new ResponseEntity<CardAndSetsDTO>(dto, HttpStatus.OK);
		} else {
			return new ResponseEntity<CardAndSetsDTO>(dto, HttpStatus.NO_CONTENT);
		}
	}
	
}
