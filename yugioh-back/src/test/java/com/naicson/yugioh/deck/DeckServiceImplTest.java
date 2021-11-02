package com.naicson.yugioh.deck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.naicson.yugioh.dao.DeckDAO;
import com.naicson.yugioh.dto.set.DeckDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.sets.DeckUsers;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.repository.sets.DeckUsersRepository;
import com.naicson.yugioh.service.DeckDetailService;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.service.UserDetailsImpl;
import com.naicson.yugioh.util.ValidObjects;
import com.naicson.yugioh.util.exceptions.ErrorMessage;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class DeckServiceImplTest {
	
	DeckDetailService deckService;
	
	@MockBean
	DeckRepository deckRepository;
	@MockBean
	RelDeckCardsRepository relDeckCardsRepository;
	@MockBean
	DeckUsersRepository deckUserRepository;
	@MockBean
	DeckDAO dao;
	
	@BeforeEach
	public void setUp() {
		this.deckService = new DeckServiceImpl(deckRepository, relDeckCardsRepository, dao,  deckUserRepository);
		this.mockAuth();
		
	}
	
	
	@Test
	public void findADeckByTheId() throws Exception {
		Deck deck = ValidObjects.generateValidDeck();
		deck.setId(1L);
		
		Mockito.when(deckRepository.findById(deck.getId())).thenReturn(Optional.of(deck));
		
		Deck deckFound = deckService.findById(deck.getId());
		
		assertThat(deckFound).isNotNull();
		assertThat(deck.getId()).isEqualTo(deck.getId());
		assertThat(deck.getNome()).isEqualTo("Deck Teste");
	}
	
	@Test
	public void returnCardsOfaDeck() {
		
		List<RelDeckCards> rels = new ArrayList<>();		
		RelDeckCards rel = ValidObjects.generateRelDeckCards();
		rel.setId(1L);
		rels.add(rel);
		
		Deck deck = ValidObjects.generateValidDeck();
		deck.setId(1L);
		
		Mockito.when(relDeckCardsRepository.findByDeckId(deck.getId())).thenReturn(rels);
		
		List<RelDeckCards> relReturned = deckService.relDeckCards(deck.getId());
		
		assertThat(relReturned).isNotEmpty();
		assertThat(relReturned).isNotNull();
		assertThat(relReturned.get(0).getId()).isEqualTo(rel.getId());
		assertTrue(relReturned.stream().anyMatch(item -> "YYYY-1111".equals(rel.getCard_set_code())));
		
	}

	@Test
	public void addSetToUsersCollection() throws SQLException, ErrorMessage, Exception {
		
		Long originalDeckId = 1L;
		
		Deck deckOrigem = ValidObjects.generateValidDeck();
		deckOrigem.setId(1L);
		
		DeckUsers newDeck = new DeckUsers();
		newDeck.setId(2L);
		
		DeckUsers generatedDeckId = new DeckUsers();
		generatedDeckId.setId(2L);
		
		DeckDetailService deckMock = Mockito.spy(deckService);
		
		Mockito.when(deckRepository.findById(originalDeckId)).thenReturn(Optional.of(deckOrigem));
		Mockito.when(deckUserRepository.save(any(DeckUsers.class))).thenReturn(generatedDeckId);
		Mockito.when(deckService.addCardsToUserDeck(originalDeckId, generatedDeckId.getId())).thenReturn(1);
		//Mockito.when(deckMock.addOrRemoveCardsToUserCollection(originalDeckId, 1, "A")).thenReturn(40);
		Mockito.doReturn(40).when(deckMock).addOrRemoveCardsToUserCollection(originalDeckId, 1, "A");
		
		int qtdCardsAdded = deckMock.addSetToUserCollection(originalDeckId);
		
		assertThat(qtdCardsAdded == 40);
		
	}
	
	@Test
	public void addingCardToUserCollection() throws SQLException, ErrorMessage {		
		
		UserDetailsImpl user = ValidObjects.generateValidUser();
		Long originalDeckId = 1L;
		int itemAtualizado = 1;
		
		DeckDetailService deckMock = Mockito.spy(deckService);		
		
		Mockito.when(dao.verifyIfUserAleadyHasTheDeck(originalDeckId, user.getId())).thenReturn(0);
		Mockito.when(dao.addDeckToUserCollection(originalDeckId, user.getId())).thenReturn(itemAtualizado);
		Mockito.doReturn(1).when(deckMock).addOrRemoveCardsToUserCollection(originalDeckId, 1, "A");
		
		int retorno = deckMock.ImanegerCardsToUserCollection(originalDeckId, "A");
		
		assertThat(retorno >= 1);
		
	}
	
	@Test
	public void validUserDontHaveDeckAndFlagAreRemove() throws SQLException, ErrorMessage {
			
		Long originalDeckid = 1L;			
		Mockito.when(dao.verifyIfUserAleadyHasTheDeck(originalDeckid,1)).thenReturn(0);	
		int retorno = deckService.ImanegerCardsToUserCollection(originalDeckid, "R");
		
		assertThat(retorno == 0);		
		
	}
	
	@Test
	public void addCardToUserCollectionHavingCardFalse() throws SQLException, ErrorMessage {
	
		Long originalDeckId = 1L;
		UserDetailsImpl user = ValidObjects.generateValidUser();
		
		List<DeckDTO> listDeckDTO = new ArrayList<>();
		
		DeckDTO deckOne = ValidObjects.generateValidDeckDTO(1);		
		DeckDTO deckTwo = ValidObjects.generateValidDeckDTO(2);
		
		listDeckDTO.add(deckOne);
		listDeckDTO.add(deckTwo);
		
		Mockito.when(dao.relationDeckAndCards(originalDeckId)).thenReturn(listDeckDTO);
		Mockito.when(dao.verifyIfUserAleadyHasTheCard(user.getId(), "")).thenReturn(false);
		Mockito.when(dao.insertCardToUserCollection(any())).thenReturn(1);
		
		int qtdCardsAddedOrRemoved = deckService.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(), "A");
		
		assertThat(qtdCardsAddedOrRemoved == 2);
		
	}
	
	@Test
	public void addCardToUserCollectionHavingCardTrue() throws SQLException, ErrorMessage {
	
		Long originalDeckId = 1L;
		UserDetailsImpl user = ValidObjects.generateValidUser();
		
		List<DeckDTO> listDeckDTO = new ArrayList<>();
		
		DeckDTO deckOne = ValidObjects.generateValidDeckDTO(1);		
		DeckDTO deckTwo = ValidObjects.generateValidDeckDTO(2);
		
		listDeckDTO.add(deckOne);
		listDeckDTO.add(deckTwo);
		
		Mockito.when(dao.relationDeckAndCards(originalDeckId)).thenReturn(listDeckDTO);
		Mockito.when(dao.verifyIfUserAleadyHasTheCard(user.getId(), "")).thenReturn(true);
		Mockito.when(dao.changeQuantityOfEspecifCardUserHas(user.getId(), "", "A")).thenReturn(1);
		Mockito.when(dao.insertCardToUserCollection(any())).thenReturn(1);
		
		int qtdCardsAddedOrRemoved = deckService.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(), "A");
		
		assertThat(qtdCardsAddedOrRemoved == 2);
		
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
