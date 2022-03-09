package com.naicson.yugioh.dto.cards;

import java.util.Map;

import com.naicson.yugioh.entity.Card;

public class CardDetailsDTO {
	
	private Card card;
	private Map<String, Integer> qtdUserHaveByKonamiCollection;
	private Map<String,Integer> qtdUserHaveByUserCollection;
	

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
	
	
}
