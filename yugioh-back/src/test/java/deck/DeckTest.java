package deck;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.service.UserDetailsImpl;
import com.naicson.yugioh.util.ErrorMessage;

/*@ExtendWith(SpringExtension.class)
@DataJpaTest*/

@ActiveProfiles("test")
@SpringBootTest(classes={com.naicson.yugioh.YugiohApplication.class})
@RunWith(MockitoJUnitRunner.class)
public class DeckTest {
	
	@Autowired
	private DeckServiceImpl deckService;
	@Autowired
	private  DeckRepository deckRepository;	
	@Autowired
	private  RelDeckCardsRepository relDeckCardRepository;	
	@Mock
	private Authentication auth;
	
	
	
	@Test
	@DisplayName("#deck")
	public void procurarDeckPorId() throws Exception {
		  Deck deck = new Deck();
		  deck.setId(2);
		  deck.setNome("Teste");
		  deck.setUserId(2);
		  deck.setDt_criacao(new Date());		  	
		  		  
		  Deck dk = deckRepository.save(deck);
		  System.out.println(dk.toString());
		  Assert.assertNotNull(dk);
	}
	
	@Test
	@DisplayName("#addSetToUserCollection")
	public void adicionarSetAColecaoDoUsuario() throws Exception {
		  	
			UserDetailsImpl user = new UserDetailsImpl();
		  	user.setId(2);
		  	
		  	 Authentication authentication = Mockito.mock(Authentication.class);
			 // Mockito.whens() for your authorization object
			 SecurityContext securityContext = Mockito.mock(SecurityContext.class);
			 Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
			 SecurityContextHolder.setContext(securityContext);
			 Mockito.when(authentication.getPrincipal()).thenReturn(user);
		    
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
		    Deck dk = deckRepository.save(newDeck);
		    
		    RelDeckCards rel = new RelDeckCards();		   
		    rel.setDeck_id(dk.getId());
		    rel.setCard_numero(555);
		    rel.setCard_price(1.99);
		    rel.setCard_raridade("Common");
		    rel.setCard_set_code("ABCD-1900");
		    rel.setDt_criacao(new Date());   
		    
		    RelDeckCards rel2 = new RelDeckCards();		   
		    rel2.setDeck_id(dk.getId());
		    rel2.setCard_numero(444);
		    rel2.setCard_price(1.00);
		    rel2.setCard_raridade("Rare");
		    rel2.setCard_set_code("ZYX-9999");		    
		    rel2.setDt_criacao(new Date());		  
		    
		    relDeckCardRepository.save(rel);
		    relDeckCardRepository.save(rel2);
		    
		    int qtdAdded = deckService.addSetToUserCollection(dk.getId());
		  	
		    Assert.assertEquals(qtdAdded, 2 );
		  		  
	}
	
	@Test
	public void removerSestDaColecaoDoUsuario() throws SQLException, ErrorMessage, Exception {
		 UserDetailsImpl user = new UserDetailsImpl();
	  	 user.setId(2);
	  	
	  	 Authentication authentication = Mockito.mock(Authentication.class);
		 // Mockito.whens() for your authorization object
		 SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		 Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		 SecurityContextHolder.setContext(securityContext);
		 Mockito.when(authentication.getPrincipal()).thenReturn(user);
		 	
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
			
		    Deck dk = deckRepository.save(newDeck);
		    
		    RelDeckCards rel = new RelDeckCards();		   
		    rel.setDeck_id(dk.getId());
		    rel.setCard_numero(555);
		    rel.setCard_price(1.99);
		    rel.setCard_raridade("Common");
		    rel.setCard_set_code("ABCD-1900");
		    rel.setDt_criacao(new Date());
		    
		    relDeckCardRepository.save(rel);
		    
		    int removed = deckService.removeSetFromUsersCollection(dk.getId());
		    
		    Optional<Deck> deckSearched = deckRepository.findById(dk.getId());
		    
		    Assert.assertEquals(1, removed);
		    Assert.assertEquals(deckSearched, Optional.empty());
		 
	}
	
	
}
