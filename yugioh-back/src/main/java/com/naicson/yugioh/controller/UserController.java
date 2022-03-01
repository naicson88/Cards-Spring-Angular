package com.naicson.yugioh.controller;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.auth.User;
import com.naicson.yugioh.repository.UserRepository;

@RestController
@RequestMapping({"yugiohAPI/User"})
@CrossOrigin(origins = "angular.path", maxAge = 3600)
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path = {"/{id}"})
	public Optional<User> listarId(@PathVariable("id") Integer id) {	
		return userRepository.findById(id);
	}
	
	@GetMapping("consulta-usuario/{username}")
	public ResponseEntity<User> consultarUsuario(@PathVariable("username") String username) {
		User user =  userRepository.findByUserName(username)
				.orElseThrow(() -> new EntityNotFoundException("Entity not found. Username: " + username));
		
		user.setEmail(null);
		user.setPassword(null);
		
		return new  ResponseEntity<User>(user, HttpStatus.OK);
	}
}
