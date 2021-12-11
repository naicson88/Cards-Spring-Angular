package com.naicson.yugioh.dao;

import java.lang.annotation.Native;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.stereotype.Repository;

import com.naicson.yugioh.dto.RelUserCardsDTO;

@Repository
public class CardDAO {
	
	@PersistenceContext
	 EntityManager em;
	
	public List<RelUserCardsDTO> searchForCardsUserHave(Integer userId, String cardsNumbers) {		
		Query query = em.createNativeQuery(" SELECT * FROM tab_rel_user_cards WHERE user_id = :userId and card_numero in (" + cardsNumbers + ")", RelUserCardsDTO.class)
				.setParameter("userId", userId);
		
		@SuppressWarnings("unchecked")
		List<RelUserCardsDTO> relList = query.getResultList();
		
		return relList;
	}
	
	public List<Tuple> listCardOfUserDetails(Long cardNumber, Integer userId) throws SQLException, Exception {
		Query query = em.createNativeQuery("select du.nome , rel.card_set_code , rel.card_raridade as rarity,\r\n"
			+ " rel.card_price as price, count(rel.card_set_code) as quantity\r\n"
			+ "from tab_deck_users du \r\n"
			+ "inner join tab_rel_deckusers_cards rel on rel.deck_id = du.id \r\n"
			+ "where rel.card_numero = :cardNumber and du.user_id = :userId", Tuple.class)
				.setParameter("cardNumber", cardNumber)
				.setParameter("userId", userId);
		
		@SuppressWarnings("unchecked")
		List<Tuple> resultCards = query.getResultList();
		
		return resultCards;
	}
	
	
}
