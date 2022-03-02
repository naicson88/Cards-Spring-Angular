package com.naicson.yugioh.service.card;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.home.RankingForHomeDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.stats.CardPriceInformation;
import com.naicson.yugioh.repository.CardPriceInformationRepository;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.service.HomeServiceImpl;
import com.naicson.yugioh.service.interfaces.CardPriceInformationService;
import com.naicson.yugioh.util.enums.CardStats;
import com.naicson.yugioh.util.exceptions.ErrorMessage;

@Service
public class CardPriceInformationServiceImpl implements CardPriceInformationService{

	@Autowired
	private CardPriceInformationRepository cardInfoRepository;
	
	@Autowired
	private CardRepository cardRepository;

	Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);	
	
	@Override
	public void calculateWeeklyPercentageVariable(CardPriceInformation cardInfo)  {
		
		this.validateWeeklyPercentageObj(cardInfo);
		
		Double diference = cardInfo.getCurrentPrice() - cardInfo.getPrice2();
		
		if(diference != 0.0) {
			Double percentDiff = (diference / cardInfo.getPrice2()) * 100;
			cardInfo.setWeeklyPercentVariable(new BigDecimal(percentDiff).setScale(2, RoundingMode.HALF_UP).doubleValue());
		} else {
			cardInfo.setWeeklyPercentVariable(diference);
		}
		
		cardInfo.setLastUpdate(LocalDateTime.now());
		cardInfoRepository.save(cardInfo);
	
	}
	
	private void validateWeeklyPercentageObj(CardPriceInformation cardInfo)  {
		
		try {
			if(cardInfo == null) {
				logger.error("CARD INFORMATION WAS NULL, CANNOT CALCULATE WEEKLY PERCENTAGE");
				throw new ErrorMessage("Card Information is null, cannot calculate weekly percentage");
			}
			
			if(cardInfo.getCurrentPrice() == null || cardInfo.getCurrentPrice() == 0.0 ) {
				logger.error("Card current price is invalid to calculate weekly percentage".toUpperCase());
				throw new ErrorMessage("Card current price is invalid to calculate weekly percentage");
			}
			
			if(cardInfo.getPrice2() == null || cardInfo.getPrice2() == 0.0 ) {
				logger.error("Card price_2  is invalid to calculate weekly percentage".toUpperCase());
				throw new ErrorMessage("Card price_2 is invalid to calculate weekly percentage");
			}
		}catch(Exception e){
			e.getMessage();
		}
		
		
	}
	
	@Override
	public List<RankingForHomeDTO> getWeeklyHighStats() {	
		List<RankingForHomeDTO> highCards = this.findWeeklyCards(CardStats.HIGH);
		return highCards;		
	}
	
	@Override
	public List<RankingForHomeDTO> getWeeklyLowStats() {	
		List<RankingForHomeDTO> lowCards = this.findWeeklyCards(CardStats.LOW);	
		return lowCards;		
	}
	
	@Override
	public List<RankingForHomeDTO> findWeeklyCards(CardStats stats) {
		
		List<RankingForHomeDTO> rankingByStatsList = new ArrayList<>();
		
		try {
			
			if(stats == null){
				logger.error("Invalid stats for weekly consulting".toUpperCase());
				throw new IllegalArgumentException("Invalid stats for weekly consulting");
			}
			
			CardStats.valueOf(stats.toString());
			List<CardPriceInformation> cardsByStats =  stats.getStats(cardInfoRepository);
			
			this.validadeStatsList(cardsByStats, stats);
			
			 rankingByStatsList = this.convertFromCardInfoToRankingDTO(cardsByStats, stats);
			
			if(rankingByStatsList == null || rankingByStatsList.isEmpty()) {
				logger.error("Ranking stats list is empty".toUpperCase());
				throw new NoSuchElementException("Ranking stats list is empty");
			}
				
		}catch(Exception e) {
			logger.error("Something bad happened: " + e.getMessage());
		}
		
		return rankingByStatsList;		
	}

	private List<RankingForHomeDTO> convertFromCardInfoToRankingDTO(List<CardPriceInformation> cardsByStats, CardStats stats) {
		this.validadeStatsList(cardsByStats, stats);
		
		List<RankingForHomeDTO> listCards = cardsByStats.stream().map(card -> {			
			String cardName = this.getCardName(Long.parseLong(card.getCardNumber()));
			RankingForHomeDTO cardByStats = new RankingForHomeDTO();
			
			cardByStats.setCardName(cardName);
			cardByStats.setCardNumber(card.getCardNumber());
			cardByStats.setCardPrice(card.getCurrentPrice());
			cardByStats.setPercentVariable(card.getWeeklyPercentVariable());
			cardByStats.setSetCode(card.getCardSetCode());
			
			return cardByStats;
		}).collect(Collectors.toList());
		
		return listCards;
	}
	
	private String getCardName(Long cardNumber) {
		
		Card card = cardRepository.findByNumero(cardNumber);
		
		if(card == null) {
			logger.error("Card with number " + cardNumber + " not found.".toUpperCase());
			throw new EntityNotFoundException("Card with number " + cardNumber + " not found.");
		}
		
		if(card.getNome() == null || card.getNome().isBlank()) {
			logger.error("Invalid card name of card = " + card.getNumero());
			throw new NoSuchElementException("Invalid card name of card = " + card.getNumero() + "".toUpperCase());
		}
		
		return card.getNome();		
	}
	
	
	private void validadeStatsList(List<CardPriceInformation> cardsByStats, CardStats stats) {
		
		try {
			if(cardsByStats == null || cardsByStats.isEmpty()) {
				logger.error("List of cards with stats " + stats.toString() + " is empty".toUpperCase());
				throw new ErrorMessage("List of cards with stats " + stats.toString() + " is empty");
			}
		}catch(Exception e) {
			logger.error("Something bad happened: " + e.getMessage());
		}
		
	}


}
