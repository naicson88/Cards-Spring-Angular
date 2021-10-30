package com.naicson.yugioh.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.service.CardDetailService;
import com.naicson.yugioh.service.CardServiceImpl;
import com.naicson.yugioh.service.UserDetailsImpl;
import com.naicson.yugioh.util.ErrorMessage;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CardServiceImplTest {
	
	CardDetailService cardService;
	
	@MockBean
	private CardRepository cardRepository;
	@MockBean
	private CardDAO dao;
	
	@BeforeEach
	public void setUp() {
		this.cardService = new CardServiceImpl(cardRepository, dao);
		this.mockAuth();
	}
	
	@Test
	private void searchCardsUserHave() throws SQLException, ErrorMessage {
		int[] cardsNumbers = new int[] {111,222};
		
		RelUserCardsDTO rel1 = this.generateRelUserCardsDTO(111);
		RelUserCardsDTO rel2 = this.generateRelUserCardsDTO(222);
		
		List<RelUserCardsDTO> list = new ArrayList<>();
		list.add(rel1);
		list.add(rel2);
		
		Mockito.when(dao.searchForCardsUserHave(anyInt(), anyString())).thenReturn(list);
		
		List<RelUserCardsDTO> listReturned = cardService.searchForCardsUserHave(cardsNumbers);
		
		assertEquals(listReturned.size(), 2);
		assertThat(listReturned.get(0).getCardNumero() == 111);
		assertThat(listReturned.get(1).getCardNumero() == 222);
		
	}

	private void mockAuth() {
		UserDetailsImpl user = this.generateValidUser();
		
		Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
	}
	
	private UserDetailsImpl generateValidUser() {
		UserDetailsImpl user = new UserDetailsImpl();
		user.setEmail("usertest@hotmail.com");
		user.setId(1);
		user.setPassword("123456");
		user.setUsername("Alan Naicson");
		
		return user;
	}
	
	private RelUserCardsDTO generateRelUserCardsDTO(int cardNumero) {
		RelUserCardsDTO rel = new RelUserCardsDTO();
		rel.setCardNumero(cardNumero);
		rel.setCardSetCode("xxx");
		rel.setDtCriacao(new Date());
		rel.setId(1);
		rel.setQtd(3);
		rel.setUserId(1);
		
		return rel;
	}
}
