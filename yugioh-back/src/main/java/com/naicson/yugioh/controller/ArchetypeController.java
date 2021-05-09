package com.naicson.yugioh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Archetype;
import com.naicson.yugioh.repository.ArchetypeRepository;

@RestController
@RequestMapping({"yugiohAPI/arch"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ArchetypeController {
	
	@Autowired
	ArchetypeRepository archRepository;
	
	@GetMapping("/all")
	public List<Archetype> consultar(){
		return archRepository.findAll();
	}
	
	
}
