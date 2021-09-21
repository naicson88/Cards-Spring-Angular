package com.naicson.yugioh.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping(path = {"/{UtilInsertOnDeck}"})
	public int adicionarCardDeck() {
		int qtdCardsInseridos = 0;
		String nome_set = "Emperor of Darkness Structure Deck";
		String nome_set_formatado = "Emperor%20of%20Darkness";
		Integer deck_id = 12;
		
		try {
			return  qtdCardsInseridos =  utilService.adicionarCardDeck(nome_set, nome_set_formatado, deck_id, Utilitarios.URL);
		} catch (Exception e) {	
			e.getMessage();		
		}
		return qtdCardsInseridos;
	}
	
	@PostMapping(path = {"/card"})
	public int adicionarCardOnTabCards() {
		int qtdCardsInseridos = 0;
		
		boolean isPt = true;
		Long card_numero = 11050416L;
		String URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?id="+card_numero;
		String imagem_url = "..\\\\..\\\\assets\\\\img\\\\monsters\\\\Token\\\\"+card_numero+".jpg";
		
		try {
			qtdCardsInseridos = utilService.adicionarCardsTAB_CARDS(card_numero, imagem_url, isPt, URL);
		}catch(Exception e) {
			e.getMessage();
		}
		
		return qtdCardsInseridos;
	}
	
	
}
