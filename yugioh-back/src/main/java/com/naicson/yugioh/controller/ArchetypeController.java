package com.naicson.yugioh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Archetype;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.repository.ArchetypeRepository;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.service.CardServiceImpl;

@RestController
@RequestMapping({"yugiohAPI/arch"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ArchetypeController {
	
	@Autowired
	ArchetypeRepository archRepository;
	
	@Autowired
	CardServiceImpl cardService;
	
	@GetMapping("/all")
	public List<Archetype> consultar(){
		return archRepository.findAll();
	}

	@GetMapping("/archetype/{archId}")
	public Archetype consultarPorId( @PathVariable("archId") int archId) {
		Archetype arch = new Archetype();
		arch = archRepository.findById(archId);
		List<Card> arrCards = cardService.encontrarPorArchetype(archId);
		arch.setArrayCards(arrCards); 
		
		return arch;
	}
	
	
}
