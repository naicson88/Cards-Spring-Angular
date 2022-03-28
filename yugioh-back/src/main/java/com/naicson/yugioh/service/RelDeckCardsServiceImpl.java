package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.service.interfaces.RelDeckCardsDetails;

@Service
public class RelDeckCardsServiceImpl implements RelDeckCardsDetails {
	
	@Autowired
	RelDeckCardsRepository relDeckCardsRepository;
	
	Logger logger = LoggerFactory.getLogger(RelDeckCardsServiceImpl.class);

	@Override
	public List<RelDeckCards> saveRelDeckCards(List<RelDeckCards> listRelDeckCards) throws Exception, IllegalArgumentException, SQLException {
		
		List<RelDeckCards> relSaved = new ArrayList<>();
		
		if(listRelDeckCards == null || listRelDeckCards.size() == 0) {
			logger.error("Invalid list of Rel Deck Cards".toUpperCase());
			throw new IllegalArgumentException("Invalid list of Rel Deck Cards");
		}
		
				listRelDeckCards.stream().forEach(rel -> {
					
					if(relDeckCardsRepository.findByCardSetCode(rel.getCard_set_code()) == null ||
							relDeckCardsRepository.findByCardSetCode(rel.getCard_set_code()).isEmpty()	) {
						relSaved.add(relDeckCardsRepository.save(rel));
						
					} else {
						throw new IllegalArgumentException(" Card set code already registered: " + rel.getCard_set_code());
					}

				});
				
				if(listRelDeckCards.size() != relSaved.size()) {
					throw new Exception("It was not possible save all Relations");
				}	
				
			return relSaved;
	}

}
