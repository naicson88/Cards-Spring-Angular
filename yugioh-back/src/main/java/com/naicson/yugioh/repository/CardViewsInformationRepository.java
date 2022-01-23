package com.naicson.yugioh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.stats.CardViewsInformation;

@Repository
public interface CardViewsInformationRepository extends JpaRepository<CardViewsInformation, Long> {
	
	List<CardViewsInformation> findTop6ByOrderByQtdViewsWeeklyDesc();
}
