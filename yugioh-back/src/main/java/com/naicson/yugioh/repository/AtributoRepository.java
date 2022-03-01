package com.naicson.yugioh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naicson.yugioh.entity.Atributo;

public interface AtributoRepository extends JpaRepository<Atributo, Long>{

	Atributo findByName(String trim);

}
