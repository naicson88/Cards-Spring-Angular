package com.naicson.yugioh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.Sets;

@Service
public interface CardDetailService {
	
	List<Card> listar();
	Card listarId(int id);
	Card add(Card card);
	Card editar(Card card);
	Card deletar (int id);
	
	Card cardDetails(Integer id);
	List<Deck> cardDecks(String cardNumero);
	
	Card encontrarPorNumero(String numero);
}
