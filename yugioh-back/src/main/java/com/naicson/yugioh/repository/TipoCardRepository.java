package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.TipoCard;

@Repository
public interface TipoCardRepository extends JpaRepository<TipoCard, Long> {

	TipoCard findByName(String race);

}
