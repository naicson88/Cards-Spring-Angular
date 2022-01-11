package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.dto.home.HomeDTO;


@Repository
public interface HomeRepository extends JpaRepository<HomeDTO, Long>{
	
	@Query(value = "select  count(set_type) from tab_deck_users as du where set_type = :setType and du.user_id = :userId ", nativeQuery = true)
	long returnQuantitySetType(String setType, Long userId);
	
	@Query(value = "select  count(card_numero) "
			+ " from tab_rel_deckusers_cards rel "
			+ " inner join tab_deck_users du on du.id = rel.deck_id "
			+ " where du.user_id = :userId ", nativeQuery = true)
	long returnQuantityCardsUserHave(long userId);
	
}
