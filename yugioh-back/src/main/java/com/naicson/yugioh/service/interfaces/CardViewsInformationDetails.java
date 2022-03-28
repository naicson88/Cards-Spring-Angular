package com.naicson.yugioh.service.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.home.RankingForHomeDTO;
import com.naicson.yugioh.entity.stats.CardViewsInformation;

@Service
public interface CardViewsInformationDetails {
	
	public List<RankingForHomeDTO> getWeeklyMostViewed();
	CardViewsInformation updateCardViewsOrInsertInDB(Long cardNumber);

}
