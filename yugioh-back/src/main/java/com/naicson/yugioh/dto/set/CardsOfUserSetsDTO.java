package com.naicson.yugioh.dto.set;

import java.math.BigInteger;

public class CardsOfUserSetsDTO {
	
	private String setName;
	private String cardSetCode;
	private String rarity;
	private Double price;
	private BigInteger quantity;
	
	public CardsOfUserSetsDTO() {
		
	}
	
	
	public CardsOfUserSetsDTO(String setName, String cardSetCode, String rarity, Double price, BigInteger quantity) {
		super();
		this.setName = setName;
		this.cardSetCode = cardSetCode;
		this.rarity = rarity;
		this.price = price;
		this.quantity = quantity;
	}


	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	public String getCardSetCode() {
		return cardSetCode;
	}
	public void setCardSetCode(String cardSetCode) {
		this.cardSetCode = cardSetCode;
	}
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public BigInteger getQuantity() {
		return quantity;
	}
	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}
	
	
}
