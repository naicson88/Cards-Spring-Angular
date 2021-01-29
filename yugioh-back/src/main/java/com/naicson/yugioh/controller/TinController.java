package com.naicson.yugioh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Tin;
import com.naicson.yugioh.repository.TinRepository;

@RestController
@RequestMapping({"yugiohAPI/Tins"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TinController {
	
	@Autowired
	private TinRepository tinRepository;
	
	@GetMapping("/todos")
	public List<Tin> consultar(){
		return tinRepository.findAll();
	}
	
	@GetMapping("/por-nome")
	public List<Tin> consultarPorNome(String nomeTin){
		return tinRepository.findByNameContaining(nomeTin);
	}
}
