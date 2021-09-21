package com.naicson.yugioh.repository.sets;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.sets.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long>{
	List<Box> findAll();
	Box findById(int id);
	Box save (Card card);
	void delete (Box card);
	
	List<Box> findByNomeContaining(String nomeBox);
	
}
