package com.naicson.yugioh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.repository.CardRepository;

@Service
public class CardServiceImpl implements CardDetailService {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Override
	public List<Card> listar() {
		// TODO Auto-generated method stub
		return cardRepository.findAll();
	}

	@Override
	public Card listarId(int id) {
		// TODO Auto-generated method stub
		return cardRepository.findById(id);
	}

	@Override
	public Card add(Card card) {
		// TODO Auto-generated method stub
		return cardRepository.save(card);
	}

	@Override
	public Card editar(Card card) {
		// TODO Auto-generated method stub
		return cardRepository.save(card);
	}

	@Override
	public Card deletar(int id) {
		// TODO Auto-generated method stub
		Card card = cardRepository.findById(id);
		if(card != null) {
			cardRepository.delete(card);
		}
		
		return card;
	}
	
}
