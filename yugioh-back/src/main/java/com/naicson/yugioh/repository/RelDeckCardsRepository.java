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

	List<RelDeckCards> findByDeckId(Integer deckId);
	
	List<RelDeckCards> findByDeckIdAndCardNumber(Integer deckId, Long cardNumero);

	/*
	 * * // Preenche o deck apenas com a relação de card que contenha esse card
	 * numbere
	 * 
	 * 
	 * @Transactional public List<RelDeckCards> relDeckAndCards(Integer deck_id,
	 * Integer card_number) { Query query = em.createNativeQuery(
	 * " select * from tab_rel_deck_cards where deck_id= :deck_id AND card_numero = :card_number"
	 * , RelDeckCards.class); List<RelDeckCards> rel = (List<RelDeckCards>)
	 * query.setParameter("deck_id", deck_id) .setParameter("card_number",
	 * card_number).getResultList();
	 * 
	 * return rel; }s
	 */
	 
	
}
