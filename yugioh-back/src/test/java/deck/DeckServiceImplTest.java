package deck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.service.DeckDetailService;
import com.naicson.yugioh.service.DeckServiceImpl;

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
	
	@BeforeEach
	public void setUp() {
		this.deckService = new DeckServiceImpl(deckRepository, relDeckCardsRepository);
	}
	
	
	@Test
	public void findADeckByTheId() {
		Deck deck = this.generateValidDeck();
		deck.setId(1);
		
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
		rel.setId(1);
		rels.add(rel);
		
		Deck deck = this.generateValidDeck();
		deck.setId(1);
		
		Mockito.when(relDeckCardsRepository.findByDeckId(deck.getId())).thenReturn(rels);
		
		List<RelDeckCards> relReturned = deckService.relDeckCards(deck.getId());
		
		assertThat(relReturned).isNotEmpty();
		assertThat(relReturned).isNotNull();
		assertThat(relReturned.get(0).getId()).isEqualTo(rel.getId());
		assertTrue(relReturned.stream().anyMatch(item -> "YYYY-1111".equals(rel.getCard_set_code())));
		
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
		relDeckCards.setDeckId(1);
		relDeckCards.setDt_criacao(new Date());
		
		return relDeckCards;
	}
}
