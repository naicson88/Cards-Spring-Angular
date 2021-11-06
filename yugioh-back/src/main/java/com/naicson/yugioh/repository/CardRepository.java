 package com.naicson.yugioh.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.util.search.CardSpecification;


@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
	
	List<Card> findAll();
	
	Card findById(int id);
	
	Card save (Card card);
	
	Card findByNumero(Long numero);
	
	void delete (Card card);
	
	Card findByNumero(String numero);
	
	@Query(value = "SELECT * FROM tab_cards WHERE COD_ARCHETYPE = :archId",  nativeQuery = true)	
	List<Card> findByArchetype(int archId);
	
	@Query(value = "SELECT * FROM tab_cards ORDER BY RAND() LIMIT 30",  nativeQuery = true)
	List<Card> findRandomCards();
	
	@Query(value = "SELECT * FROM tab_cards cards "
			+ " inner join tab_rel_deck_cards rcards on rcards.card_numero = cards.numero "
			+ " inner join tab_decks deck on deck.id = rcards.deck_id "
			+ " where deck.user_id = :userId and cards.generic_type = :type ",
			nativeQuery = true)
	Page<Card> findCardsByTypeAndUser(String type, int userId, Pageable page);
	
	@Query(value = " SELECT DISTINCT * FROM yugioh.tab_cards CARDS "
			+ " INNER JOIN TAB_REL_DECKUSERS_CARDS UCARDS ON UCARDS.CARD_NUMERO = CARDS.NUMERO "
			+ " INNER JOIN TAB_DECK_USERS DUSERS ON DUSERS.ID = UCARDS.DECKUSER_ID "
			+ " WHERE CARDS.GENERIC_TYPE = :genericType AND DUSERS.USER_ID = :userId "
			+ " GROUP BY CARDS.NUMERO ",
			countQuery = "SELECT count(*) FROM tab_cards",
			nativeQuery=true)
	Page<Card> getByGenericType (Pageable page, String genericType, Integer userId);
	
	@Query(value = " SELECT DISTINCT * FROM yugioh.tab_cards CARDS "
			+ " INNER JOIN TAB_REL_DECKUSERS_CARDS UCARDS ON UCARDS.CARD_NUMERO = CARDS.NUMERO "
			+ " INNER JOIN TAB_DECK_USERS DUSERS ON DUSERS.ID = UCARDS.DECKUSER_ID "
			+ " WHERE CARDS.nome like CONCAT('%',:cardName,'%') AND DUSERS.USER_ID = :userId "
			+ " GROUP BY CARDS.NUMERO ",
			countQuery = "SELECT count(*) FROM tab_cards",
			nativeQuery=true)
	Page<Card> cardSearchByNameUserCollection(String cardName, int userId, Pageable pageable);
	
	
}
