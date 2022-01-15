package com.naicson.yugioh.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.home.HomeDTO;
import com.naicson.yugioh.dto.home.LastAddedDTO;
import com.naicson.yugioh.dto.home.RankingForHomeDTO;
import com.naicson.yugioh.entity.sets.SetType;

import com.naicson.yugioh.repository.HomeRepository;
import com.naicson.yugioh.service.interfaces.HomeDetailService;
import com.naicson.yugioh.util.GeneralFunctions;
import com.naicson.yugioh.util.enums.CardStats;
import com.naicson.yugioh.util.exceptions.ErrorMessage;

@Service
public class HomeServiceImpl implements HomeDetailService{
	
	@Autowired
	HomeRepository homeRepository;
	
	@Autowired
	CardPriceInformationServiceImpl cardInfoService;
	@Autowired
	CardViewsInformationServiceImpl cardViewService;

	Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);	
	
	@Override
	public HomeDTO getHomeDto() {
		HomeDTO homeDto = new HomeDTO();
		UserDetailsImpl user = GeneralFunctions.userLogged();
		
		try {
			
		homeDto.setQtdDeck(homeRepository.returnQuantitySetType(SetType.DECK.getType(), user.getId()));
		homeDto.setQtdBoxes(homeRepository.returnQuantitySetType(SetType.BOX.getType(), user.getId()));
		homeDto.setQtdTins(homeRepository.returnQuantitySetType(SetType.TIN.getType(), user.getId()));
		homeDto.setQtdCards(homeRepository.returnQuantityCardsUserHave(user.getId()));
		
		List<Tuple> lastSets = homeRepository.returnLastSetsAddedToUser(user.getId());
		List<Tuple> lastCardsAdded = homeRepository.lastCardsAddedToUser(user.getId());
		
		homeDto.setLastSets(this.lastSetsAddedToUser(lastSets));
		homeDto.setLastCards(this.lastCardsAddedToUsuer(lastCardsAdded));
		//Thid map will bring the high, low and view as key
		homeDto.setHighCards(this.cardInfoService.getWeeklyHighStats());
		homeDto.setLowCards(this.cardInfoService.getWeeklyLowStats());
		homeDto.setWeeklyMostView(this.cardViewService.getWeeklyMostViewed());	
			
		} catch (ErrorMessage e) {
			e.getMessage();
		}
		
		return homeDto;
	}
	
	private List<LastAddedDTO> lastSetsAddedToUser(List<Tuple> sets) throws ErrorMessage {
		
		List<LastAddedDTO> lastsSetsAdded = new ArrayList<>();
		
			if(sets != null && !sets.isEmpty()) {
				//Lasts cards or decks added
					lastsSetsAdded = sets.stream().map(set ->{
					LastAddedDTO lastSet = new LastAddedDTO();				
					lastSet.setId(set.get(0,BigInteger.class).longValue());
					lastSet.setImg(set.get(1, String.class));
					lastSet.setName(set.get(2, String.class));
					lastSet.setPrice(0.0);
					lastSet.setSetCode("WWW-EN001");
					
					return lastSet;
				}).collect(Collectors.toList());
				
				if(lastsSetsAdded == null || lastsSetsAdded.isEmpty()) {
					logger.error("LIST WITH LASTS ADDED IS EMPTY");
					throw new ErrorMessage("List with lasts added is empty");
				}
								
			} else {
				 return Collections.emptyList();
			}
		
		return lastsSetsAdded;
	}
	
	private List<LastAddedDTO> lastCardsAddedToUsuer(List<Tuple> lastCardsAddedTuple){
		List<LastAddedDTO> lastCardsAdded = new ArrayList<>();
		
			if(lastCardsAddedTuple != null && !lastCardsAddedTuple.isEmpty()) {
				
				lastCardsAdded = lastCardsAddedTuple.stream().map(card -> {
					LastAddedDTO lastCard = new LastAddedDTO();
					lastCard.setCardNumber(card.get(0, Integer.class).longValue());
					lastCard.setName(card.get(1, String.class));
					lastCard.setSetCode(card.get(2, String.class));
					lastCard.setPrice(card.get(3, Double.class));
					
					this.calculate("HIGH");
					
					return lastCard;
				}).collect(Collectors.toList());
				
			}else {
				return Collections.emptyList();
			}
			
		return lastCardsAdded;
	}
	
/*	private void saveInfoCard(LastAddedDTO lastCardAdded) {
		CardExtraInformation info = new CardExtraInformation();
		info.setCardNumber(String.valueOf(lastCardAdded.getCardNumber()));
		info.setCardSetCode(lastCardAdded.getSetCode());
		info.setCurrentPrice(lastCardAdded.getPrice());
		info.setPrice2(0.1);
		info.setPrice3(5.61);
		info.setPrice4(3.63);
		info.setPrice5(1.99);
		info.setTotalQtdViews(70L);
		info.setWeeklyPercentVariable(3.30);
		info.setWeeklyQtdViews(10L);
		
		cardInfoRepository.save(info);
	}*/
	
	private void calculate(String stats) {
		cardInfoService.findWeeklyCards(CardStats.LOW);
	}
}
