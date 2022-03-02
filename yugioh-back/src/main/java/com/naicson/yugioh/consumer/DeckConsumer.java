package com.naicson.yugioh.consumer;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.naicson.yugioh.dto.KonamiDeck;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.service.DeckServiceImpl;
import com.naicson.yugioh.service.RelDeckCardsServiceImpl;
import com.naicson.yugioh.service.card.CardRegistry;


@Component
public class DeckConsumer {
	
	@Autowired
	RelDeckCardsServiceImpl relDeckCardsService;
	
	@Autowired
	DeckServiceImpl deckService;
	
	@Autowired
	CardRegistry cardRegistry;
	
	Logger logger = LoggerFactory.getLogger(DeckConsumer.class);
	
	//@RabbitListener(queues = "${rabbitmq.queue.deck}")
	@Transactional
	private void consumer(KonamiDeck kDeck) {
		
		try {
			
			cardRegistry.RegistryCardFromYuGiOhAPI(kDeck);
			
			Deck newDeck = this.createNewDeck(kDeck);
			
			newDeck = deckService.countQtdCardRarityInTheDeck(newDeck);
			
			Long deckId = deckService.saveKonamiDeck(newDeck).getId();
			
			newDeck = this.setDeckIdInRelDeckCards(newDeck, deckId);
			
			relDeckCardsService.saveRelDeckCards(newDeck.getRel_deck_cards());
			
			logger.info("Deck successfully saved!");
			
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage());
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
		deck.setNome(this.adjustDeckName(kDeck.getNome()));
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
	
	private String adjustDeckName(String rawName) {
		
		if(StringUtils.containsIgnoreCase(rawName,"Structure" )) {
			rawName = rawName.replace("Structure", "");

			if(StringUtils.containsIgnoreCase(rawName,"Deck"))
				rawName = rawName.replace("Deck", "");
	
			if(StringUtils.containsIgnoreCase(rawName,":"))
				rawName = rawName.replace(":", "");
		}
					
		return rawName.trim();
	}
	
}
