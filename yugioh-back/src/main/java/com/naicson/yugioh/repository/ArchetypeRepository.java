package com.naicson.yugioh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Archetype;


@Repository
public interface ArchetypeRepository extends JpaRepository<Archetype, Integer>{

	Optional<Archetype> findById(Integer archId);

	Archetype findByArcName(String archetype);

}
