package com.naicson.yugioh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.sets.Tin;

@Repository
public interface TinRepository extends JpaRepository<Tin, Integer>{
	
	public List<Tin> findByNameContaining(String nomeTin);
}
