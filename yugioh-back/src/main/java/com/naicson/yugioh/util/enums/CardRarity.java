package com.naicson.yugioh.util.enums;

public enum CardRarity {
	
	COMMON("Common"),
	RARE("Rare"),
	SUPER_RARE("Super Rare"),
	ULTRA_RARE("Ultra Rare");
	
	private final String cardRarity;
	
	CardRarity(String rarity){
		cardRarity = rarity;
	}
	
	public String getCardRarity() {
		return cardRarity;
	}
	
}
