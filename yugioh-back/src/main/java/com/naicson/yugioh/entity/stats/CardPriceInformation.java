package com.naicson.yugioh.entity.stats;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_card_price_info")
public class CardPriceInformation {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cardNumber;
	@Column(unique=true)
	private String cardSetCode;
	private Double currentPrice;
	private Double price2;
	private Double price3;
	private Double price4;
	private Double price5;
	private Double weeklyPercentVariable;
	private LocalDateTime lastUpdate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardSetCode() {
		return cardSetCode;
	}
	public void setCardSetCode(String cardSetCode) {
		this.cardSetCode = cardSetCode;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Double getPrice2() {
		return price2;
	}
	public void setPrice2(Double price2) {
		this.price2 = price2;
	}
	public Double getPrice3() {
		return price3;
	}
	public void setPrice3(Double price3) {
		this.price3 = price3;
	}
	public Double getPrice4() {
		return price4;
	}
	public void setPrice4(Double price4) {
		this.price4 = price4;
	}
	public Double getPrice5() {
		return price5;
	}
	public void setPrice5(Double price5) {
		this.price5 = price5;
	}
	public Double getWeeklyPercentVariable() {
		return weeklyPercentVariable;
	}
	public void setWeeklyPercentVariable(Double weeklyPercentVariable) {
		this.weeklyPercentVariable = weeklyPercentVariable;
	}
	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
}
