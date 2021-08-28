package com.naicson.yugioh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.naicson.yugioh.entity.RelDeckCards;

public interface RelDeckCardsRepository extends JpaRepository<RelDeckCards, Integer>{
	
	@Query(value = " select * from tab_rel_deck_cards rel "
			+ " inner join tab_decks deck on deck.id = rel.deck_id "
			+ " inner join tab_cards card on card.numero = rel.card_numero "
			+ " where card_numero = :cardNumber and deck.is_konami_deck = 'S'",			
			nativeQuery = true)
	List<RelDeckCards> findCardByNumberAndIsKonamiDeck(Long cardNumber);
	
}
