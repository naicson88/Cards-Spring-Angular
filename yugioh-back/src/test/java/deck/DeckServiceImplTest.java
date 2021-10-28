package deck;

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
import org.springframework.security.test.context.support.WithUserDetails;
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
import com.naicson.yugioh.util.ErrorMessage;

/*@ExtendWith(SpringExtension.class)
@DataJpaTest*/

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
	public void findADeckByTheId() {
		Deck deck = this.generateValidDeck();
		deck.setId(1L);
		
		Mockito.when(deckRepository.findById(deck.getId())).thenReturn(Optional.of(deck));
		
		Deck deckFound = deckService.findById(deck.getId()).get();
		
		assertThat(deckFound).isNotNull();
		assertThat(deck.getId()).isEqualTo(deck.getId());
		assertThat(deck.getNome()).isEqualTo("Deck Teste");
	}
	
	@Test
	public void returnCardsOfaDeck() {
		
		List<RelDeckCards> rels = new ArrayList<>();		
		RelDeckCards rel = this.generateRelDeckCards();	
		rel.setId(1L);
		rels.add(rel);
		
		Deck deck = this.generateValidDeck();
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
		
		Deck deckOrigem = this.generateValidDeck();
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
		
		UserDetailsImpl user = this.generateValidUser();
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
		UserDetailsImpl user = this.generateValidUser();
		
		List<DeckDTO> listDeckDTO = new ArrayList<>();
		
		DeckDTO deckOne = this.generateValidDeckDTO(1);		
		DeckDTO deckTwo = this.generateValidDeckDTO(2);
		
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
		UserDetailsImpl user = this.generateValidUser();
		
		List<DeckDTO> listDeckDTO = new ArrayList<>();
		
		DeckDTO deckOne = this.generateValidDeckDTO(1);		
		DeckDTO deckTwo = this.generateValidDeckDTO(2);
		
		listDeckDTO.add(deckOne);
		listDeckDTO.add(deckTwo);
		
		Mockito.when(dao.relationDeckAndCards(originalDeckId)).thenReturn(listDeckDTO);
		Mockito.when(dao.verifyIfUserAleadyHasTheCard(user.getId(), "")).thenReturn(true);
		Mockito.when(dao.changeQuantityOfEspecifCardUserHas(user.getId(), "", "A")).thenReturn(1);
		Mockito.when(dao.insertCardToUserCollection(any())).thenReturn(1);
		
		int qtdCardsAddedOrRemoved = deckService.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(), "A");
		
		assertThat(qtdCardsAddedOrRemoved == 2);
		
	}
	
	
	private DeckDTO generateValidDeckDTO(Integer id) {
		DeckDTO dto = new DeckDTO();
		dto.setId(id);
		dto.setCard_numero(123456);
		dto.setCard_price(1.99);
		dto.setCard_raridade("Rare");
		dto.setCard_set_code("000-yyyy");
		
		return dto;
	}
	
	private Deck generateValidDeck() {
		
		Deck newDeck = new Deck();
		   
		newDeck.setImagem("Imagem Deck");
		newDeck.setNome("Deck Teste");
		newDeck.setNomePortugues("Deck teste Portugues");
		newDeck.setSetType("UD");
		newDeck.setQtd_cards(10);
		newDeck.setQtd_comuns(5);
		newDeck.setQtd_raras(11);
		newDeck.setQtd_secret_raras(15);
		newDeck.setQtd_super_raras(10);
		newDeck.setQtd_ulta_raras(2);
		newDeck.setIsKonamiDeck("N");
		newDeck.setDt_criacao(new Date());
		newDeck.setCopiedFromDeck(10);
		newDeck.setUserId(2); 			  		  
	  
	  return newDeck;  

	}
	
	private Card generateValidCard() {
		Card card = new Card();
		
		card.setArquetipo("Arquetipo Teste");
		card.setAtk(2000);
		card.setAtributo("EARTH");
		card.setCategoria("Monster");
		card.setCodArchetype(10);
		card.setDef(1500);
		card.setDescr_pendulum("Descricao Pendulum Teste");
		card.setEscala(8);
		
		return card;
	}
	
	private RelDeckCards generateRelDeckCards() {
		RelDeckCards relDeckCards = new RelDeckCards();
		
		relDeckCards.setCard_numero(9999999L);
		relDeckCards.setCard_price(90.58);
		relDeckCards.setCard_raridade("R");
		relDeckCards.setCard_set_code("YYYY-1111");
		relDeckCards.setDeckId(1L);
		relDeckCards.setDt_criacao(new Date());
		
		return relDeckCards;
	}
	
	private UserDetailsImpl generateValidUser() {
		UserDetailsImpl user = new UserDetailsImpl();
		user.setEmail("usertest@hotmail.com");
		user.setId(1);
		user.setPassword("123456");
		user.setUsername("Alan Naicson");
		
		return user;
	}
	
	
	private void mockAuth() {
		UserDetailsImpl user = this.generateValidUser();
		
		Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
}
}
