package com.naicson.yugioh.repository;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {

	
	List<Deck> findByNomeContaining(String nomeDeck);

	
}
