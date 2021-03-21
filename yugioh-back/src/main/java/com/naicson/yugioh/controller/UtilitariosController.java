package com.naicson.yugioh.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naicson.yugioh.entity.Utilitarios;
import com.naicson.yugioh.repository.UtilitariosRepository;

import com.naicson.yugioh.service.UtilitariosServiceImpl;

@RestController
@RequestMapping({"yugiohAPI/util"})
public class UtilitariosController {

	@Autowired
	UtilitariosServiceImpl utilService;
	
	@GetMapping(path = {"/{InsertCardsDeck}"})
	public int adicionarCardDeck() {
		int qtdCardsInseridos = 0;
		String nome_set = "Starter Deck: Yuya";
		String nome_set_formatado = "Starter%20Deck:%20Yuya";
		Integer deck_id = 8;
		
		try {
			return  qtdCardsInseridos =  utilService.adicionarCardDeck(nome_set, nome_set_formatado, deck_id, Utilitarios.URL);
		} catch (Exception e) {	
			e.getMessage();		
		}
		return qtdCardsInseridos;
	}
}
