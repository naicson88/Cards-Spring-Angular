package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naicson.yugioh.entity.RelDeckCards;

public interface RelDeckCardsRepository extends JpaRepository<RelDeckCards, Integer>{
	
}
