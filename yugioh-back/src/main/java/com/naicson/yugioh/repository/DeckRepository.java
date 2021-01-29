package com.naicson.yugioh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
	
	List<Deck> findByNomeContaining(String nomeDeck);
}
