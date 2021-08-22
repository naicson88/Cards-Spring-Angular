package com.naicson.yugioh.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {


	List<Deck> findByNomeContaining(String nomeDeck);
	
	Page<Deck> findAllBySetType(String setType, Pageable pageable);
	Page<Deck> findAllByUserId(int userId, Pageable pageable);
	
	List<Deck> findAllByIdIn(int[] ids);
	
	List<Deck> findAllByUserId(int userId);
	
	
}
