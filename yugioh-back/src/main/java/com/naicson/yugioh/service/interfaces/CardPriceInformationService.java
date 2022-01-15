package com.naicson.yugioh.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.home.RankingForHomeDTO;
import com.naicson.yugioh.entity.stats.CardPriceInformation;
import com.naicson.yugioh.util.enums.CardStats;

@Service
public interface CardPriceInformationService {
	
	public void calculateWeeklyPercentageVariable(CardPriceInformation cardInfo);
	
	public List<RankingForHomeDTO> getWeeklyHighStats();
	
	public List<RankingForHomeDTO> getWeeklyLowStats();
	
	public List<RankingForHomeDTO> findWeeklyCards(CardStats stats);
}
