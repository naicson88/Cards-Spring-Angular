package com.naicson.yugioh.dto.home;

public class RankingForHomeDTO {
	
	private String cardName;
	private Long cardNumber;
	private Double percentVariable;
	private Double cardPrice;
	private int qtdViews;
	
	public RankingForHomeDTO() {
		
	}

	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public Long getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}


	public Double getPercentVariable() {
		return percentVariable;
	}


	public void setPercentVariable(Double percentVariable) {
		this.percentVariable = percentVariable;
	}


	public Double getCardPrice() {
		return cardPrice;
	}


	public void setCardPrice(Double cardPrice) {
		this.cardPrice = cardPrice;
	}


	public int getQtdViews() {
		return qtdViews;
	}


	public void setQtdViews(int qtdViews) {
		this.qtdViews = qtdViews;
	}
	
	
}
