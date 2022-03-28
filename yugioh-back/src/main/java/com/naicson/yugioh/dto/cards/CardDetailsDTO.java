package com.naicson.yugioh.dto.cards;

import java.util.List;
import java.util.Map;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.stats.CardPriceInformation;
import com.naicson.yugioh.entity.stats.CardViewsInformation;

public class CardDetailsDTO {
	
	private Card card;
	private Map<String, Integer> qtdUserHaveByKonamiCollection;
	private Map<String,Integer> qtdUserHaveByUserCollection;
	private List<CardPriceInformation> prices;
	private CardViewsInformation views;
	

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Map<String, Integer> getQtdUserHaveByKonamiCollection() {
		return qtdUserHaveByKonamiCollection;
	}

	public void setQtdUserHaveByKonamiCollection(Map<String, Integer> qtdUserHaveByKonamiCollection) {
		this.qtdUserHaveByKonamiCollection = qtdUserHaveByKonamiCollection;
	}

	public Map<String,Integer> getQtdUserHaveByUserCollection() {
		return qtdUserHaveByUserCollection;
	}

	public void setQtdUserHaveByUserCollection(Map<String,Integer> qtdUserHaveByUserCollection) {
		this.qtdUserHaveByUserCollection = qtdUserHaveByUserCollection;
	}

	public List<CardPriceInformation> getPrices() {
		return prices;
	}

	public void setPrices(List<CardPriceInformation> prices) {
		this.prices = prices;
	}

	public CardViewsInformation getViews() {
		return views;
	}

	public void setViews(CardViewsInformation views) {
		this.views = views;
	}
	
	
	
}
