package com.naicson.yugioh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.sets.Box;
import com.naicson.yugioh.repository.sets.BoxRepository;

@RestController
@RequestMapping({"yugiohAPI/Boxes"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class BoxController {
	
	@Autowired
	BoxRepository boxRepository;
	
	@GetMapping("/todos")
	public List<Box> consultar(){
		return boxRepository.findAll();
	}
	
	@GetMapping("/por-nome")
	public List<Box> porNome(String nomeBox){
		return boxRepository.findByNomeContaining(nomeBox);
	}
}
