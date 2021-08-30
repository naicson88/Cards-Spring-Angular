package com.naicson.yugioh.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.dto.cards.CardAndSetsDTO;
import com.naicson.yugioh.dto.cards.CardsSearchDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.util.CardSpecification;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer>, JpaSpecificationExecutor<Card> {
	
	List<Card> findAll();
	Card findById(int id);
	Card save (Card card);
	Card findByNumero(Integer numero);
	
	void delete (Card card);
	
	Card findByNumero(String numero);
	
	@Query(value = "SELECT * FROM tab_cards WHERE COD_ARCHETYPE = :archId",  nativeQuery = true)	
	List<Card> findByArchetype(int archId);
	
	@Query(value = "SELECT * FROM tab_cards ORDER BY RAND() LIMIT 30",  nativeQuery = true)
	List<Card> findRandomCards();
	
	@Query(value = "SELECT * FROM yugioh.tab_cards cards "
			+ " inner join tab_rel_deck_cards rcards on rcards.card_numero = cards.numero "
			+ " inner join tab_decks deck on deck.id = rcards.deck_id "
			+ " where deck.user_id = :userId and cards.generic_type = :type ",
			nativeQuery = true)
	Page<Card> findCardsByTypeAndUser(String type, int userId, Pageable page);
	
	Page<Card> findAllByGenericType (Pageable page, String genericType);
	
}
