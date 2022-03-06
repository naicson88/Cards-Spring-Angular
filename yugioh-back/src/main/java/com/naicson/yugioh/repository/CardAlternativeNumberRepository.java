package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naicson.yugioh.entity.CardAlternativeNumber;

public interface CardAlternativeNumberRepository extends JpaRepository<CardAlternativeNumber, Integer> {

	CardAlternativeNumber findByCardAlternativeNumber(Long id);

}
