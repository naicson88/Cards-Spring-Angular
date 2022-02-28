package com.naicson.yugioh.util.enums;

public enum CardProperty {
	
	CONTINUOUS("Continuous"),
	QUICK_PLAY("Quick-Play"),
	EQUIP("Equip"),
	NORMAL("Normal"),
	FIELD("Field"),
	RITUAL("Ritual"),
	COUNTER("Counter");
	
	
	private final String cardRarity;
	
	CardProperty(String rarity){
		cardRarity = rarity;
	}
	
	public String getCardRarity() {
		return cardRarity;
	}
}
