package com.naicson.yugioh.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.consumer.DeckConsumer;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.service.interfaces.RelDeckCardsDetails;

@Service
public class RelDeckCardsServiceImpl implements RelDeckCardsDetails {
	
	@Autowired
	RelDeckCardsRepository relDeckCardsRepository;
	
	Logger logger = LoggerFactory.getLogger(DeckConsumer.class);

	@Override
	public List<RelDeckCards> saveRelDeckCards(List<RelDeckCards> listRelDeckCards) throws Exception {
		
		if(listRelDeckCards == null || listRelDeckCards.size() == 0) {
			logger.error("Invalid list of Rel Deck Cards".toUpperCase());
			throw new IllegalArgumentException("Invalid list of Rel Deck Cards");
		}
			List<RelDeckCards> relSaved = new ArrayList<>();
			
			listRelDeckCards.stream().forEach(rel -> {
				
				try {
				
					if(relDeckCardsRepository.findByCardSetCode(rel.getCard_set_code()) == null) {
						relSaved.add(relDeckCardsRepository.save(rel));
						
					} else {
						logger.error("Card set code already registered {}".toUpperCase(), rel.getCard_set_code());
						throw new Exception(" Card set code already registered: " + rel.getCard_set_code());
					}
				
				}catch(Exception e) {
					logger.error(e.getMessage());
					 e.getMessage();
				}
			});
			
			if(listRelDeckCards.size() != relSaved.size()) {
				logger.error("It was not possible save all Relations".toUpperCase());
				throw new Exception("It was not possible save all Relations");
			}
			
			return relSaved;

	}

}
