package com.naicson.yugioh.serviceTest;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.service.DeckServiceImpl;


//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DeckServiceImplTest {

	@InjectMocks
	 DeckServiceImpl deckService;

	@MockBean
	private CardRepository cardRepository;
	
	@Before
	public void testaSalvarCard() {
		Card card = new Card();
		card.setArquetipo("arquetipoTeste");
		card.setAtk(1500);
		card.setAtributo("DARK");
		card.setCategoria("Assassin");
		card.setDef(2500);
		card.setDescr_pendulum("DescricaoTeste");
		card.setId(5);
		card.setNome("Dark Magician");
		card.setNumero(91628319);
		card.setRaridade("rare");
		card.setId(2);
		
		cardRepository.save(card);
		
		verify(cardRepository, times(1)).save(card);
	}
	
	@Test
	public void consultaCard() {
		int id = 2;
		Card card = new Card();
		card = cardRepository.findById(id);
		
		assertNotNull(card);
		
	}
}
