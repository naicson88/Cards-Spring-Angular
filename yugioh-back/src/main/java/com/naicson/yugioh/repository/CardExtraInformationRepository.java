package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naicson.yugioh.entity.CardExtraInformation;

public interface CardExtraInformationRepository extends JpaRepository<CardExtraInformation, Long> {

}
