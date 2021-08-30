package com.naicson.yugioh.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.naicson.yugioh.dto.DeckDTO;
import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.util.ErrorMessage;

@Repository
@Transactional
public class DeckDAO {
	
	@PersistenceContext
	 EntityManager em;
	
	 Query query = null;
	 
	 @Autowired
	 DeckRepository deckRepository;
	

	public int addDeckToUserCollection(Integer deckId, int userId) throws SQLException {
		Query query = em.createNativeQuery("INSERT INTO TAB_REL_USER_DECK (user_id, deck_id, qtd) values(:user_id, :deck_id, 1)")
				.setParameter("user_id", userId)
				.setParameter("deck_id", deckId);
		
		BigInteger inserted = (BigInteger) query.getSingleResult();
		int id = (int) inserted.longValue();
		
		return id;			
		
	}
	
	public int addDeck(Deck deck) throws SQLException, ErrorMessage {
		
		if(deck == null) {
			throw new ErrorMessage("Deck is null.");
		}	
		
			Deck d = deckRepository.save(deck);
			deckRepository.flush();
			
			return d.getId();
			
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
	
	public int insertCardToUserCollection(RelUserCardsDTO rel) throws SQLException {
		
		Query query = em.createNativeQuery(" INSERT INTO tab_rel_user_cards (user_id, card_numero, card_set_code, qtd, dt_criacao)"
				+ " VALUES (:userId, :card_number, :card_set_code, :qtd, :dt_criacao) ")
				.setParameter("userId", rel.getUserId())
				.setParameter("card_number", rel.getCardNumero())
				.setParameter("card_set_code", rel.getCardSetCode())
				.setParameter("qtd", rel.getQtd())
				.setParameter("dt_criacao", rel.getDtCriacao());
		
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

	public List<RelUserDeckDTO> searchForDecksUserHave(Integer userId, String decksIds) {		
		Query query = em.createNativeQuery(
				" SELECT DK.id, DK.user_id, KONAMI_DECK_COPIED AS deck_id, COUNT(KONAMI_DECK_COPIED) AS qtd " +
				" FROM TAB_DECK_USERS DK " + 
				" WHERE USER_ID = :userId and KONAMI_DECK_COPIED IN (" +  decksIds + ")" +
				" GROUP BY (KONAMI_DECK_COPIED) ", RelUserDeckDTO.class)
				
				.setParameter("userId", userId);
		
		@SuppressWarnings("unchecked")
		List<RelUserDeckDTO> relList = query.getResultList();
		
		return relList;
	}

	public int addCardsToDeck(Integer generatedDeckId, Long originalDeckId) {
		int result = 0;
		
		if(generatedDeckId != null && originalDeckId != null) {
			Query query = em.createNativeQuery(" INSERT INTO tab_rel_deckusers_cards (DECKUSER_ID, CARD_NUMERO,CARD_RARIDADE,CARD_SET_CODE,CARD_PRICE, DT_CRIACAO) "+
											  " SELECT " + generatedDeckId + " , CARD_NUMERO,CARD_RARIDADE,CARD_SET_CODE,CARD_PRICE, CURDATE() FROM TAB_REL_DECK_CARDS " +
											  " where deck_id = " + originalDeckId  );
			
			 result = query.executeUpdate();
		}
		
		return result;
	}

	
	public void removeCardsFromSet(Integer setId) throws SQLException, Exception, ErrorMessage {
		
		if(setId == null || setId == 0) {
			throw new ErrorMessage("Set id was not informed.");
		}		
		Query query = em.createNativeQuery("DELETE FROM tab_rel_deck_cards WHERE deck_id = :setId")
				.setParameter("setId", setId);	
		
		query.executeUpdate();
	}
		
}
