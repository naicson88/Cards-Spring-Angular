package com.naicson.yugioh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Card;

@Service
public interface CardDetailService {
	
	List<Card> listar();
	Card listarId(int id);
	Card add(Card card);
	Card editar(Card card);
	Card deletar (int id);
	
}
