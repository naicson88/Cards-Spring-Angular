package com.naicson.yugioh.serviceTest;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.naicson.yugioh.controller.AuthController;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.service.CardDetailService;
import com.naicson.yugioh.service.CardServiceImpl;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.util.CardSpecification;
import com.naicson.yugioh.util.SearchCriteria;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CardSpecification.class)
public class DeckServiceImplTest {
	
	/*
	 * @Autowired CardServiceImpl cardRepository;
	 * 
	 * private Card card;
	 * 
	 * @Before public void init() { card = new Card();
	 * card.setNome("Dark Magician"); }
	 * 
	 * @Test public void firtTest() { CardSpecification spec = new
	 * CardSpecification(new SearchCriteria("nome", ":", "dark magician"));
	 * 
	 * List<Card> results = cardRepository.findAll(spec);
	 * 
	 * System.out.println(results.get(0).toString()); }
	 */
}
