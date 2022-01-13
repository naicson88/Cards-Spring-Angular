package com.naicson.yugioh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_card_extra_info")
public class CardExtraInformation {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cardNumber;
	@Column(unique=true)
	private String cardSetCode;
	private Long weeklyQtdViews;
	private Long totalQtdViews;
	private Double currentPrice;
	private Double price2;
	private Double price3;
	private Double price4;
	private Double price5;
	private Double weeklyPercentVariable;
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
	public Long getWeeklyQtdViews() {
		return weeklyQtdViews;
	}
	public void setWeeklyQtdViews(Long weeklyQtdViews) {
		this.weeklyQtdViews = weeklyQtdViews;
	}
	public Long getTotalQtdViews() {
		return totalQtdViews;
	}
	public void setTotalQtdViews(Long totalQtdViews) {
		this.totalQtdViews = totalQtdViews;
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
	
}
