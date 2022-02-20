package com.naicson.yugioh.consumer;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.naicson.yugioh.dto.KonamiDeck;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.service.DeckServiceImpl;


@Component
public class DeckConsumer {
	
	@Autowired
	DeckRepository deckRepository;
	
	@Autowired
	RelDeckCardsRepository relDeckCardsRepository;
	
	@Autowired
	DeckServiceImpl deckService;
	
	Logger logger = LoggerFactory.getLogger(DeckConsumer.class);
	
	@RabbitListener(queues = "${rabbitmq.queue.deck}")
	@Transactional
	private void consumer(KonamiDeck kDeck) {
		
		System.out.println(kDeck.toString());
		
		try {
			
			Deck newDeck = this.createNewDeck(kDeck);
			
			newDeck = deckService.countQtdCardRarityInTheDeck(newDeck);
			
			Long deckId = deckRepository.save(newDeck).getId();
			
			newDeck = this.setDeckIdInRelDeckCards(newDeck, deckId);
			
			relDeckCardsRepository.saveAll(newDeck.getRel_deck_cards());
			
			logger.info("Deck successfully saved!");
			
		} catch(Exception e) {
			logger.error(e.getMessage());
		}			
	}
	
	private Deck createNewDeck(KonamiDeck kDeck) {
		
		if(kDeck == null) {
			throw new IllegalArgumentException("Informed Konami Deck is invalid!");
		}
		
		Deck deck = new Deck();
		deck.setDt_criacao(new Date());
		deck.setImagem(kDeck.getImagem());
		deck.setLancamento(kDeck.getLancamento());
		deck.setNome(kDeck.getNome());
		deck.setNomePortugues(kDeck.getNomePortugues());
		deck.setRel_deck_cards(kDeck.getListRelDeckCards());
		deck.setSetType(kDeck.getSetType());
		
		logger.info("Deck created!");
		return deck;
	}
	
	private Deck setDeckIdInRelDeckCards(Deck newDeck, Long deckId) {
		
		if(deckId == null || deckId == 0) {
			throw new IllegalArgumentException("Generated Deck Id is invalid.");
		}
		
		newDeck.getRel_deck_cards().stream().forEach(rel -> {rel.setDeckId(deckId);});
		logger.info("Deck's Id setted");
		return newDeck;
	}
	
}
