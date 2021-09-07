package com.naicson.yugioh.dto.set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tab_rel_deck_cards")
@Entity
public class DeckDTO {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int deck_id;
	private int card_numero;
	private String card_raridade;
	private String card_set_code;
	private Double card_price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeck_id() {
		return deck_id;
	}
	public void setDeck_id(int deck_id) {
		this.deck_id = deck_id;
	}
	public int getCard_numero() {
		return card_numero;
	}
	public void setCard_numero(int card_numero) {
		this.card_numero = card_numero;
	}
	public String getCard_raridade() {
		return card_raridade;
	}
	public void setCard_raridade(String card_raridade) {
		this.card_raridade = card_raridade;
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
	
	
	
}
