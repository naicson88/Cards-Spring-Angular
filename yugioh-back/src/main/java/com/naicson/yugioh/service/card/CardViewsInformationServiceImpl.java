package com.naicson.yugioh.service.card;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.home.RankingForHomeDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.stats.CardViewsInformation;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.repository.CardViewsInformationRepository;
import com.naicson.yugioh.service.HomeServiceImpl;
import com.naicson.yugioh.service.interfaces.CardViewsInformationDetails;
import com.naicson.yugioh.util.enums.CardStats;

@Service
public class CardViewsInformationServiceImpl implements CardViewsInformationDetails {
	
	@Autowired
	private CardViewsInformationRepository cardViewsRepository;
	@Autowired
	private CardRepository cardRepository;
	
	Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);	
	
	@Override
	public List<RankingForHomeDTO> getWeeklyMostViewed(){
	
			CardStats stats = CardStats.VIEW;
			CardStats.valueOf(CardStats.VIEW.toString());
			List<CardViewsInformation> cardViews = stats.getStatsView(cardViewsRepository);
			
			List<RankingForHomeDTO> rankingList = this.fromCardViewsInfoToRankingDTO(cardViews);
			
			if(rankingList == null || rankingList.isEmpty()) {
				logger.error("Ranking list of view is empty".toUpperCase());
				throw new NoSuchElementException("Ranking list of VIEW is empty");
			} 
			
		return rankingList;
	}
	
	private List<RankingForHomeDTO> fromCardViewsInfoToRankingDTO(List<CardViewsInformation> cardViews){
		
		if(cardViews == null || cardViews.isEmpty()) {
			logger.error("Card views list is emtpy".toUpperCase());
			throw new NoSuchElementException("Card views list is emtpy");
		}
		
		List<RankingForHomeDTO> rankingViews = cardViews.stream().map(card -> {
			String cardName = this.returnCardName(Long.parseLong(card.getCardNumber()));
			
			RankingForHomeDTO cardRankingViews = new RankingForHomeDTO();
			
			cardRankingViews.setCardName(cardName);
			cardRankingViews.setCardNumber(card.getCardNumber());
			cardRankingViews.setQtdViewsWeekly(card.getQtdViewsWeekly());
			
			return cardRankingViews;
		}).collect(Collectors.toList());
		
		return rankingViews;
				
	}
	
	private String returnCardName(Long cardNumber) {
		
		if(cardNumber == null || cardNumber == 0) {
			logger.error("#rankingViews: Invalid card number".toUpperCase());
			throw new IllegalArgumentException("#rankingViews: Invalid card number");
		}
		
		Card card = cardRepository.findByNumero(cardNumber);
		
		if(card == null) {
			logger.error("Card not found. Number: " + cardNumber + "".toUpperCase());
			throw new EntityNotFoundException("Card not found. Number: " + cardNumber);
		}
		
		return card.getNome();
	}
	
}
