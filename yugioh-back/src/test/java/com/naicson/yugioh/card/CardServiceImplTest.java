package com.naicson.yugioh.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.naicson.yugioh.dao.CardDAO;
import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.cards.CardAndSetsDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.service.CardDetailService;
import com.naicson.yugioh.service.CardServiceImpl;
import com.naicson.yugioh.service.UserDetailsImpl;
import com.naicson.yugioh.util.ValidObjects;
import com.naicson.yugioh.util.exceptions.ErrorMessage;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CardServiceImplTest {
	
	CardDetailService cardService;
	
	@MockBean
	private CardRepository cardRepository;
	@MockBean
	private CardDAO dao;
	@MockBean
	private RelDeckCardsRepository relDeckCardsRepository;
	@MockBean
	private DeckRepository deckRepository;;
	
	@BeforeEach
	public void setUp() {
		this.cardService = new CardServiceImpl(cardRepository, dao, relDeckCardsRepository, deckRepository);
		this.mockAuth();
	}
	
	@Test
	public void searchCardsUserHave() throws SQLException, ErrorMessage {
		int[] cardsNumbers = new int[] {111,222};
		
		RelUserCardsDTO rel1 = ValidObjects.generateRelUserCardsDTO(111); 
		RelUserCardsDTO rel2 = ValidObjects.generateRelUserCardsDTO(222);
		
		List<RelUserCardsDTO> list = List.of(rel1, rel2);
		
		Mockito.when(dao.searchForCardsUserHave(anyInt(), anyString())).thenReturn(list);
		
		List<RelUserCardsDTO> listReturned = cardService.searchForCardsUserHave(cardsNumbers);
		
		assertEquals(listReturned.size(), 2);
		assertThat(listReturned.get(0).getCardNumero() == 111);
		assertThat(listReturned.get(1).getCardNumero() == 222);
		
	}	
	
	@Test
	public void findCardToAddInUserCollection() throws SQLException, ErrorMessage {
		Long cardNumero = 1L;
		
		Card card = ValidObjects.generateValidCard();
		
		RelDeckCards rel1 = ValidObjects.generateRelDeckCards();
		RelDeckCards rel2 = ValidObjects.generateRelDeckCards();
		List<RelDeckCards> list = List.of(rel1, rel2); //new ArrayList<>();
	
		
		Deck deck1 = Mockito.mock(Deck.class);
		Deck deck2 = Mockito.mock(Deck.class);
		
		List<Deck> listDecks = List.of(deck1, deck2);
		
		Mockito.when(cardRepository.findByNumero(cardNumero)).thenReturn(card);
		Mockito.when(relDeckCardsRepository.findCardByNumberAndIsKonamiDeck(cardNumero)).thenReturn(list);
		Mockito.when(deckRepository.findAllByIdIn(Mockito.any(Long[].class))).thenReturn(listDecks);
		
		CardAndSetsDTO dto = cardService.findCardToAddToUserCollection(cardNumero);
		
		
		assertEquals(dto.getNome(), card.getNome());
		assertEquals(dto.getNumero(), card.getNumero());
		assertNotNull(dto.getMapDeckSetcode());
		
	}
	
	
	private void mockAuth() {
		UserDetailsImpl user = ValidObjects.generateValidUser();
		
		Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
	}

	
}
