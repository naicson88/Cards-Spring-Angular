package com.naicson.yugioh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.auth.User;
import com.naicson.yugioh.entity.sets.Tin;
import com.naicson.yugioh.repository.UserRepository;

@RestController
@RequestMapping({"yugiohAPI/User"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path = {"/{id}"})
	public Optional<User> listarId(@PathVariable("id") Integer id) {	
		return userRepository.findById(id);
	}
	
	@GetMapping("consulta-usuario/{username}")
	public Optional<User> consultarUsuario(@PathVariable("username") String username) {
		return userRepository.findByUserName(username);
	}
}
