package com.naicson.yugioh.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.dto.home.HomeDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.sets.DeckUsers;


@Repository
public interface HomeRepository extends JpaRepository<HomeDTO, Long>{
	
	@Query(value = "select  count(set_type) from tab_deck_users as du where set_type = :setType and du.user_id = :userId ", nativeQuery = true)
	long returnQuantitySetType(String setType, Long userId);
	
	@Query(value = "select  count(card_numero) "
			+ " from tab_rel_deckusers_cards rel "
			+ " inner join tab_deck_users du on du.id = rel.deck_id "
			+ " where du.user_id = :userId ", nativeQuery = true)
	long returnQuantityCardsUserHave(long userId);
	
	@Query(value = "select distinct * from tab_deck_users where user_id = :userId order by dt_criacao desc limit 10", nativeQuery = true)
	List<Tuple> returnLastSetsAddedToUser(long userId);
	
	@Query(value = 	
			 " select distinct cards.numero, cards.nome, rel.card_set_code, rel.card_price from tab_cards cards "
			+ " inner join tab_rel_deckusers_cards rel on rel.card_numero = cards.numero "
			+ " inner join tab_deck_users du on du.id = rel.deck_id "
			+ " where du.user_id = :userId"
			+ " order by du.dt_criacao "
			+ " limit 10 ", nativeQuery = true)
	List<Tuple> lastCardsAddedToUser(Long userId);
	
	@Query(value = "select * from tab_decks order by dt_criacao desc limit 5", nativeQuery = true)
	List<Tuple> getHotNews();
	
	@Query(value = "select ROUND(sum(card_price),2) as total from tab_rel_deckusers_cards where deck_id = :setId", nativeQuery = true)
	Double findTotalSetPrice(Long setId);
	
	
}
