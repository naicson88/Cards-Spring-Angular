package com.naicson.yugioh.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.sets.DeckUsers;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.sets.DeckUsersRepository;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.service.UserDetailsImpl;
import com.naicson.yugioh.util.ErrorMessage;
import com.naicson.yugioh.util.GeneralFunctions;

@RestController
@RequestMapping({ "yugiohAPI/decks" })
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class DeckController {

	@Autowired
	DeckRepository deckRepository;
	@Autowired
	DeckServiceImpl deckService;
	@Autowired
	DeckUsersRepository deckUserRepository;

	Page<Deck> deckList = null;
	Page<DeckUsers> deckUserList = null;

	@GetMapping("/todos")
	public List<Deck> consultar() {
		return deckRepository.findAll();
	}

	@GetMapping("/pagination")
	public ResponseEntity<Page<Deck>> deckPagination(
			@PageableDefault(page = 0, size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			@RequestParam String setType) {

		if (!setType.equals("") && setType != null && !setType.equals("UD"))
			deckList = deckRepository.findAll(pageable);
		
		if (deckList.isEmpty()) {
			return new ResponseEntity<Page<Deck>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(deckList, HttpStatus.OK);

	}

	@GetMapping("/sets-of-user")
	public ResponseEntity<Page<DeckUsers>> setsOfUser(
			@PageableDefault(page = 0, size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			@RequestParam String setType) {
		
			switch(setType) {
				case "UD": setType =  "D"; break;
				case "UB": setType =  "B"; break;
				case "UT": setType =  "T"; break;				
			}

			UserDetailsImpl user = GeneralFunctions.userLogged();
			
			deckUserList = deckUserRepository.findAllByUserIdAndSetType(user.getId(), setType, pageable);

		if (deckUserList == null) {

			return new ResponseEntity<Page<DeckUsers>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(deckUserList, HttpStatus.OK);
	}

	@GetMapping(path = { "/{id}" })
	public Deck deckAndCards(@PathVariable("id") Long id) throws Exception {
		List<Card> cardList = deckService.cardsOfDeck(id);
	//	List<RelDeckCards> rel_deck_cards = deckService.relDeckAndCards(id);
		List<RelDeckCards> relDeckCards = deckService.relDeckCards(id);
		Deck deck = deckService.findById(id);
		deck.setCards(cardList);
		deck.setRel_deck_cards(relDeckCards);

		return deck;
	}

	@GetMapping("/por-nome")
	public List<Deck> consultarPorNome(String nomeDeck) {
		return deckRepository.findByNomeContaining(nomeDeck);
	}

	@GetMapping(path = { "/add-deck-to-user-collection/{deckId}" })
	public int addSetToUserCollection(@PathVariable("deckId") Long deckId) throws Exception, ErrorMessage {
		if (deckId != null && deckId > 0) {
			return deckService.addSetToUserCollection(deckId);
		} else {
			throw new ErrorMessage("The deck informed is not valid!");
		}
	}

	@GetMapping(path = { "/remove-set-to-user-collection/{deckId}" })
	public int removeSetFromUsersCollection(@PathVariable("deckId") Long deckId) throws Exception, ErrorMessage {
		if (deckId != null && deckId > 0) {
			return deckService.removeSetFromUsersCollection(deckId);
		} else {
			throw new ErrorMessage("The deck informed is not valid!");
		}
	}

	@GetMapping("/rel-user-decks")
	public List<RelUserDeckDTO> searchForDecksUserHave(@RequestParam Long[] decksIds) throws SQLException, ErrorMessage {
		List<RelUserDeckDTO> rel = null;

		if (decksIds != null && decksIds.length > 0) {
			rel = deckService.searchForDecksUserHave(decksIds);
		}

		return rel;
	}

}
