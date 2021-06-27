package com.naicson.yugioh.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.naicson.yugioh.dto.DeckDTO;
import com.naicson.yugioh.entity.Card;

@Repository
public class DeckDAO {

	@PersistenceContext
	 EntityManager em;
	
	
	public int addDeckToUserCollection(Integer deckId, int userId) throws SQLException {
		Query query = em.createNativeQuery("INSERT INTO TAB_REL_USER_DECK (user_id, deck_id, qtd) values(:user_id, :deck_id, 1)")
				.setParameter("user_id", userId)
				.setParameter("deck_id", deckId);
						
		return query.executeUpdate();	
	}
	
	public List<DeckDTO> relationDeckAndCards(Integer deckId) throws SQLException {
		
		Query query = em.createNativeQuery("SELECT * FROM tab_rel_deck_cards WHERE DECK_ID = :deckId", DeckDTO.class)
				.setParameter("deckId", deckId);
		
		List<DeckDTO> relationDeckAndCards = (List<DeckDTO>) query.setParameter("deckId", deckId).getResultList();
		return relationDeckAndCards;		
		
	}
	
	public boolean verifyIfUserAleadyHasTheCard(int userId, String cardSetCode) throws SQLException {
		
		Query query = em.createNativeQuery(" SELECT count(*) FROM tab_rel_user_cards WHERE CARD_SET_CODE = :cardSetCode AND USER_ID = :userId ")
				.setParameter("cardSetCode", cardSetCode)
				.setParameter("userId", userId);
		
		int has =  ((Number) query.getSingleResult()).intValue();
		
		if(has > 0)
			return true;
		else
			return false;	
	}
	

	public int verifyIfUserAleadyHasTheDeck(Integer deckId, int userId) {
		Integer has = null;
		Query query = em.createNativeQuery(" SELECT qtd FROM tab_rel_user_deck WHERE deck_id = :deckId AND USER_ID = :userId ")
				.setParameter("userId", userId)
				.setParameter("deckId", deckId);
		
		try {
			//Quando ele não acha nada, acusa um exception, este try catch é só pra ignorar. 
			 has = ((Number) query.getSingleResult()).intValue();
		} catch(NoResultException e) {
			
		}
				
		if(has != null && has > 0)
			return has;
		else
			return 0;
			
	}
	
	public int changeQuantityOfEspecifCardUserHas(int userId, String cardSetCode, String flagAddOrRemove) throws SQLException {
		
		int changed;
		
		if(flagAddOrRemove.equals("A")) {
			Query query = em.createNativeQuery(" UPDATE tab_rel_user_cards set qtd = qtd + 1 WHERE CARD_SET_CODE = :cardSetCode AND USER_ID = :userId ")
					.setParameter("cardSetCode", cardSetCode)
					.setParameter("userId", userId);
			
			changed = query.executeUpdate();
			
		} else {		
			Query query = em.createNativeQuery(" UPDATE tab_rel_user_cards set qtd = qtd - 1 WHERE CARD_SET_CODE = :cardSetCode AND USER_ID = :userId ")
					.setParameter("cardSetCode", cardSetCode)
					.setParameter("userId", userId);
			
			changed = query.executeUpdate();
		}
		
		return changed;
		
	}
	
	public int insertCardToUserCollection(int userId, String cardSetCode, Integer card_number) throws SQLException {
		
		Query query = em.createNativeQuery(" INSERT INTO tab_rel_user_cards (user_id, card_numero, card_set_code, qtd) VALUES (:userId, :card_number, :card_set_code, 1) ")
				.setParameter("userId", userId)
				.setParameter("card_number", card_number)
				.setParameter("card_set_code", cardSetCode);
		
		return query.executeUpdate();
	}

	public int changeQuantitySpecificDeckUserHas(Integer deckId, int userId, String flagAddOrRemove) {
	
		int changed;
		
		if(flagAddOrRemove.equals("A")) {
			Query query = em.createNativeQuery(" UPDATE tab_rel_user_deck set qtd = qtd + 1 WHERE deck_id = :deckId AND USER_ID = :userId ")
					.setParameter("deckId", deckId)
					.setParameter("userId", userId);
			
			changed = query.executeUpdate();
			
		} else {		
			Query query = em.createNativeQuery(" UPDATE tab_rel_user_deck set qtd = qtd - 1 WHERE deck_id = :deckId AND USER_ID = :userId ")
					.setParameter("deckId", deckId)
					.setParameter("userId", userId);
			
			changed = query.executeUpdate();
		}
		
		return changed;
	}
	
	
}
