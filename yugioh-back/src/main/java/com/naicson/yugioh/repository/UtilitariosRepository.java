package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Utilitarios;

@Repository
public interface UtilitariosRepository extends JpaRepository<Utilitarios, Integer>{
	
}
