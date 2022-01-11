package com.naicson.yugioh.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.dto.set.DeckDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.util.exceptions.ErrorMessage;

@Repository
@Transactional
public class DeckDAO {
	
	@PersistenceContext
	 EntityManager em;
	
	 Query query = null;
	 
	 @Autowired
	 DeckRepository deckRepository;
	 
	 Logger logger = LoggerFactory.getLogger(DeckServiceImpl.class);
	 
	 
	public DeckDAO() {
		
	}
	 
	 public DeckDAO(EntityManager em) {
		 this.em = em;
		
	 }

	public int addDeckToUserCollection(Long originalDeckId, long userId) throws SQLException {
		Query query = em.createNativeQuery("INSERT INTO TAB_REL_USER_DECK (user_id, deck_id, qtd) values(:user_id, :deck_id, 1)")
				.setParameter("user_id", userId)
				.setParameter("deck_id", originalDeckId);
		
		BigInteger inserted = (BigInteger) query.getSingleResult();
		int id = (int) inserted.longValue();
		
		return id;			
		
	}
	
	public Long addDeck(Deck deck) throws SQLException, ErrorMessage {
		
		if(deck == null) {
			throw new ErrorMessage("Deck is null.");
		}	
		
			Deck d = deckRepository.save(deck);
			deckRepository.flush();
			
			return d.getId();
			
	}
	
	public List<DeckDTO> relationDeckAndCards(Long originalDeckId) throws SQLException {
		
		Query query = em.createNativeQuery("SELECT * FROM tab_rel_deck_cards WHERE DECK_ID = :deckId", DeckDTO.class)
				.setParameter("deckId", originalDeckId);
		
		List<DeckDTO> relationDeckAndCards = (List<DeckDTO>) query.setParameter("deckId", originalDeckId).getResultList();
		return relationDeckAndCards;			
	}
	
	public boolean verifyIfUserAleadyHasTheCard(long userId, String cardSetCode) throws SQLException {
		
		Query query = em.createNativeQuery(" SELECT count(*) FROM tab_rel_user_cards WHERE CARD_SET_CODE = :cardSetCode AND USER_ID = :userId ")
				.setParameter("cardSetCode", cardSetCode)
				.setParameter("userId", userId);
		
		int has =  ((Number) query.getSingleResult()).intValue();
		
		if(has > 0)
			return true;
		else
			return false;	
	}
	
	public int verifyIfUserAleadyHasTheDeck(Long originalDeckId, long userId) {
		Integer has = null;
		Query query = em.createNativeQuery(" SELECT qtd FROM tab_rel_user_deck WHERE deck_id = :deckId AND USER_ID = :userId ")
				.setParameter("userId", userId)
				.setParameter("deckId", originalDeckId);
		
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
	
	public int changeQuantityOfEspecifCardUserHas(long userId, String cardSetCode, String flagAddOrRemove) throws SQLException {
		
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

	public int changeQuantitySpecificDeckUserHas(Long originalDeckId, long userId, String flagAddOrRemove) {
	
		int changed;
		
		if(flagAddOrRemove.equals("A")) {
			Query query = em.createNativeQuery(" UPDATE tab_rel_user_deck set qtd = qtd + 1 WHERE deck_id = :deckId AND USER_ID = :userId ")
					.setParameter("deckId", originalDeckId)
					.setParameter("userId", userId);
			
			changed = query.executeUpdate();
			
		} else {		
			Query query = em.createNativeQuery(" UPDATE tab_rel_user_deck set qtd = qtd - 1 WHERE deck_id = :deckId AND USER_ID = :userId ")
					.setParameter("deckId", originalDeckId)
					.setParameter("userId", userId);
			
			changed = query.executeUpdate();
		}
		
		return changed;
	}

	public List<RelUserDeckDTO> searchForDecksUserHave(long userId, String decksIds) {		
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

	public int addCardsToDeck(Long originalDeckId2, Long originalDeckId) {
		int result = 0;
		
		if(originalDeckId2 != null && originalDeckId != null) {
			Query query = em.createNativeQuery(" INSERT INTO tab_rel_deckusers_cards (DECK_ID, CARD_NUMERO,CARD_RARIDADE,CARD_SET_CODE,CARD_PRICE, DT_CRIACAO) "+
											  " SELECT " + originalDeckId2 + " , CARD_NUMERO,CARD_RARIDADE,CARD_SET_CODE,CARD_PRICE, CURDATE() FROM TAB_REL_DECK_CARDS " +
											  " where deck_id = " + originalDeckId  );
			
			 result = query.executeUpdate();
		}
		
		return result;
	}

	
	public void removeCardsFromSet(Long setId) throws SQLException, Exception, ErrorMessage {
		
		if(setId == null || setId == 0) {
			throw new ErrorMessage("Set id was not informed.");
		}		
		Query query = em.createNativeQuery("DELETE FROM tab_rel_deck_cards WHERE deck_id = :setId")
				.setParameter("setId", setId);	
		
		query.executeUpdate();
	}
	
	// Traz informações completas dos cards contidos num deck
	@Transactional
	public List<Card> cardsOfDeck(Long deckId) {
		Query query = em.createNativeQuery("SELECT * FROM TAB_CARDS WHERE NUMERO IN\r\n"
				+ "(SELECT CARD_NUMERO FROM tab_rel_deck_cards WHERE DECK_ID = :deckId)\r\n" + "order by case\r\n"
				+ "when categoria LIKE 'link monster' then 1\r\n" + "when categoria like 'XYZ Monster' then 2\r\n"
				+ "when categoria like 'Fusion Monster' then 3\r\n" + "when categoria like '%Synchro%' then 4\r\n"
				+ "when categoria LIKE '%monster%' then 5\r\n" + "when categoria = 'Spell Card' then 6\r\n"
				+ "ELSE    7\r\n" + "END", Card.class);
		List<Card> cards = (List<Card>) query.setParameter("deckId", deckId).getResultList();
		return cards;
	}

	public List<Card> consultMainDeck(Long deckId) {
		Query query = null;
		
		 query = em.createNativeQuery(
				  " SELECT * FROM TAB_CARDS CARDS "
			    + " INNER JOIN tab_rel_deckusers_cards rel on rel.card_numero = cards.numero "	 
				+ " WHERE NUMERO IN "
				+ " (SELECT CARD_NUMERO FROM tab_rel_deckusers_cards WHERE DECK_ID = :deckId and is_side_deck != 1 "
				+ " AND CARDS.GENERIC_TYPE NOT IN ('XYZ', 'SYNCHRO', 'FUSION','LINK')) "
				+ " and deck_id = :deckId  and is_side_deck = 0 order by cards.nome ", Card.class);
		
		List<Card> cards = (List<Card>) query.setParameter("deckId", deckId).getResultList();
		
		return cards;
	}
	
	public List<Card> consultSideDeckCards(Long deckId, String userOrKonamiDeck) {
		
		if(deckId == null || deckId == 0)
			throw new IllegalArgumentException("Invalid Deck ID");
		
		Query query = em.createNativeQuery(" SELECT * FROM TAB_CARDS cards "
					+ " INNER JOIN tab_rel_deckusers_cards rel on rel.card_numero = cards.numero "	 
					+ " WHERE NUMERO IN "
					+ " (SELECT CARD_NUMERO FROM tab_rel_deckusers_cards WHERE DECK_ID = :deckId and is_side_deck = 1) and deck_id = :deckId  and is_side_deck = 1", Card.class);
		
		List<Card> cards = (List<Card>) query.setParameter("deckId", deckId).getResultList();
		
		return cards;
	}

	public List<Card> consultExtraDeckCards(Long deckId, String userOrKonamiDeck) {
		Query query = null;
		
		if(userOrKonamiDeck.equalsIgnoreCase("User")) {
			 query = em.createNativeQuery(
			  " SELECT * FROM TAB_CARDS CARDS "
			+ " INNER JOIN tab_rel_deckusers_cards rel on rel.card_numero = cards.numero"	 
			+ " WHERE NUMERO IN "
			+ " (SELECT CARD_NUMERO FROM tab_rel_deckusers_cards WHERE DECK_ID = :deckId and (is_side_deck = 0 or is_side_deck is null)) "
			+ " AND CARDS.GENERIC_TYPE IN ('XYZ', 'SYNCHRO', 'FUSION', 'LINK') and deck_id = :deckId  and is_side_deck = 0 order by cards.generic_type", Card.class);
			
		} else if (userOrKonamiDeck.equalsIgnoreCase("Konami")) {
			
		}else {
			throw new IllegalArgumentException("Type of deck not informed");
		}
		
		List<Card> cards = (List<Card>) query.setParameter("deckId", deckId).getResultList();
		
		return cards;
	}

	public List<RelDeckCards> relDeckUserCards(Long deckUserId) {
		Query query = em.createNativeQuery("select * from tab_rel_deckusers_cards where deck_id = :deckUserId", RelDeckCards.class);
			
		List<RelDeckCards> relation = (List<RelDeckCards>) query.setParameter("deckUserId", deckUserId).getResultList();
		
		return relation;
	}
	
	public void deleteCardsDeckuserByDeckId(Long deckUserId) throws SQLException {
		Query query = em.createNativeQuery("delete from tab_rel_deckusers_cards where deck_id = :deckUserId");
		query.setParameter("deckUserId", deckUserId);
		query.executeUpdate();
	}

	public int saveRelDeckUserCard(RelDeckCards rel, Long deckId) {
		int id = 0;
			
			Query query = em.createNativeQuery("insert into tab_rel_deckusers_cards (deck_id, card_numero, card_raridade, card_set_code, card_price, dt_criacao, "
					+ "is_side_deck) values (:deck_id,:card_numero, :card_raridade, :card_set_code, :card_price, :dt_criacao, :is_side_deck )")
			.setParameter("deck_id", deckId)
			.setParameter("card_numero", rel.getCard_numero())
			.setParameter("card_raridade", rel.getCard_raridade())
			.setParameter("card_set_code", rel.getCard_set_code())
			.setParameter("card_price", rel.getCard_price())
			.setParameter("dt_criacao", new Date())
			.setParameter("is_side_deck", rel.getIsSideDeck());
				
			 id = query.executeUpdate();	
			 
		return id;
		
	}
	
	
		
}
