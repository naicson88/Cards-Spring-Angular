package com.naicson.yugioh.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
}
