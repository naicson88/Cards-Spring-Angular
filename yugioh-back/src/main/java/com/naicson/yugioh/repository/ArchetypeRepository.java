package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Archetype;

@Repository
public interface ArchetypeRepository extends JpaRepository<Archetype, Integer >{

}
