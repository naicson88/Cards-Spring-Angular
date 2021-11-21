package com.naicson.yugioh.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.Tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.naicson.yugioh.dao.CardDAO;
import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.cards.CardAndSetsDTO;
import com.naicson.yugioh.dto.cards.CardOfUserDetailDTO;
import com.naicson.yugioh.dto.cards.CardsSearchDTO;
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
import com.naicson.yugioh.util.search.SearchCriteria;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CardServiceImplTest {
	
	@InjectMocks
	@Spy
	CardServiceImpl impl;
	
	CardDetailService cardService;
	
	@MockBean
	private CardRepository cardRepository;
	@MockBean
	private CardDAO dao;
	@MockBean
	private RelDeckCardsRepository relDeckCardsRepository;
	@MockBean
	private DeckRepository deckRepository;
	
	@Mock
	Pageable pageable;

	
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
	
	@Test
	public void cardOfUserDetail() throws SQLException, Exception {

		Card card = ValidObjects.generateValidCard();
		
		  Tuple mockedTuple = Mockito.mock(Tuple.class); 
		  List<Tuple> tupleList = List.of(mockedTuple);
		  
		Mockito.when(cardRepository.findByNumero(anyLong())).thenReturn(card);
		Mockito.when(dao.listCardOfUserDetails(anyLong(), anyInt())).thenReturn(tupleList);
		
		CardOfUserDetailDTO dto = cardService.cardOfUserDetails(1L);
		
		assertEquals(dto.getCardName(), card.getNome());
		assertEquals(dto.getCardNumber(), card.getNumero());
		assertNotNull(dto.getSetsWithThisCard());
		assertNotNull(dto.getRarity());
		
	}
	
	@Test
	public void getByGenericType() {
		
		Page<Card> pageList = Mockito.mock(Page.class);
		
		Mockito.when(cardRepository.getByGenericType(pageable,"teste", 1)).thenReturn(pageList);
		
		List<CardsSearchDTO> dto = cardService.getByGenericType(pageable,"teste", 1);
		
		assertNotNull(dto);
		assertFalse(!dto.isEmpty());
	}
	
	@Test
	public void cardSearchSuccess() {
		
		SearchCriteria criteria = Mockito.mock(SearchCriteria.class);
		List<SearchCriteria> criteriaList = List.of(criteria);
		
		List<Card> cardList = List.of(ValidObjects.generateValidCard(), ValidObjects.generateValidCard());

		Mockito.doReturn(cardList).when(impl).findAll(any()); 
		
		List<CardsSearchDTO> dto = impl.cardSearch(criteriaList, null);
		
		assertThat(!dto.isEmpty());
		assertEquals(dto.get(0).getNome(), cardList.get(0).getNome());
	}
	
	@Test
	public void cardSearchByNameUserCollection() {
		Page<Card> pageList = Mockito.mock(Page.class);
		UserDetailsImpl user = ValidObjects.generateValidUser();
		user.setId(1);
		
		Mockito.when(cardRepository.cardSearchByNameUserCollection("teste", user.getId(), pageable )).thenReturn(pageList);
		
		List<CardsSearchDTO> dto = cardService.cardSearchByNameUserCollection("teste", pageable);
		
		assertNotNull(dto);
		assertThat(!dto.isEmpty());
		assertThat(dto.size() > 0);
		
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
