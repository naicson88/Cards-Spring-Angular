package com.naicson.yugioh.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_rel_deck_cards")
public class RelDeckCards {
	
	@Id
	private Integer id;
	private Integer deck_id;
	private Integer card_numero;
	private String card_set_code;
	private Double card_price;
	private String card_raridade;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDeck_id() {
		return deck_id;
	}
	public void setDeck_id(Integer deck_id) {
		this.deck_id = deck_id;
	}
	public Integer getCard_numero() {
		return card_numero;
	}
	public void setCard_numero(Integer card_numero) {
		this.card_numero = card_numero;
	}
	public String getCard_set_code() {
		return card_set_code;
	}
	public void setCard_set_code(String card_set_code) {
		this.card_set_code = card_set_code;
	}
	public Double getCard_price() {
		return card_price;
	}
	public void setCard_price(Double card_price) {
		this.card_price = card_price;
	}
	public String getCard_raridade() {
		return card_raridade;
	}
	public void setCard_raridade(String card_raridade) {
		this.card_raridade = card_raridade;
	}	
}
