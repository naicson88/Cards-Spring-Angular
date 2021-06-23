package com.naicson.yugioh.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class DeckDAO {

	@PersistenceContext
	 EntityManager em;
	
	
	public int addDeckToUserCollection(Integer deckId, int userId) throws SQLException {
		Query query = em.createNativeQuery("INSERT INTO TAB_REL_USER_DECK (user_id, deck_id) values(:user_id, :deck_id)")
				.setParameter("user_id", userId)
				.setParameter("deck_id", deckId);
						
		return query.executeUpdate();	
	}
	
}
