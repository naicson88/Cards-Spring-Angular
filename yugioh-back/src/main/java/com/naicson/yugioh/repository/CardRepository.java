package com.naicson.yugioh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Card;

@Repository

public interface CardRepository extends JpaRepository<Card, Integer>{
	List<Card> findAll();
	Card findById(int id);
	Card save (Card card);
	void delete (Card card);

}
