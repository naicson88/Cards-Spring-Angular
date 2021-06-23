package com.naicson.yugioh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Archetype;
import com.naicson.yugioh.entity.Card;

@Repository
public interface ArchetypeRepository extends JpaRepository<Archetype, Integer>{

	Archetype findById(int archId);

}
