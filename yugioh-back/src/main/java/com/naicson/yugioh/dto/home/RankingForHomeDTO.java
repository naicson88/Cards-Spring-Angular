package com.naicson.yugioh.dto.home;

public class RankingForHomeDTO {
	
	private String cardName;
	private String cardNumber;
	private String setCode;
	private Double percentVariable;
	private Double cardPrice;
	private Long qtdViewsWeekly;
	
	public RankingForHomeDTO() {
		
	}

	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
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


	public Long getQtdViewsWeekly() {
		return qtdViewsWeekly;
	}


	public void setQtdViewsWeekly(Long long1) {
		this.qtdViewsWeekly = long1;
	}

	public String getSetCode() {
		return setCode;
	}

	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	
	
}
