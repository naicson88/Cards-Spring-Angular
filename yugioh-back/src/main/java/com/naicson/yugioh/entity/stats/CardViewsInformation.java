package com.naicson.yugioh.entity.stats;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_card_views_info")
public class CardViewsInformation {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String cardNumber;
	private Long qtdViewsWeekly;
	private Long totalQtdViews;
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
	public Long getQtdViewsWeekly() {
		return qtdViewsWeekly;
	}
	public void setQtdViewsWeekly(Long qtdViewsWeekly) {
		this.qtdViewsWeekly = qtdViewsWeekly;
	}
	public Long getTotalQtdViews() {
		return totalQtdViews;
	}
	public void setTotalQtdViews(Long totalQtdViews) {
		this.totalQtdViews = totalQtdViews;
	}
	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
}
