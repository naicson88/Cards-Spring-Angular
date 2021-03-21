package com.naicson.yugioh.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Utilitarios {
	
	@Id
	 private Integer id;
	 private String nome_set;
	 private String nome_set_formatado;
	 private Integer set_id;
	 private  Integer card_numero;
	 private  String card_raridade;
	 private  String card_set_code;
	 private  String card_price;
	 public final static String URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?set=";
	 
	 
	public String getNome_set() {
		return nome_set;
	}
	public void setNome_set(String nome_set) {
		this.nome_set = nome_set;
	}
	public Integer getSet_id() {
		return set_id;
	}
	public void setSet_id(Integer set_id) {
		this.set_id = set_id;
	}
	public Integer getCard_numero() {
		return card_numero;
	}
	public void setCard_numero(Integer card_numero) {
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
	public String getCard_price() {
		return card_price;
	}
	public void setCard_price(String card_price) {
		this.card_price = card_price;
	}
	@Override
	public String toString() {
		return "Utilitarios [nome_set=" + nome_set + ", set_id=" + set_id + ", card_numero=" + card_numero
				+ ", card_raridade=" + card_raridade + ", card_set_code=" + card_set_code + ", card_price=" + card_price
				+ "]";
	}
	public String getNome_set_formatado() {
		return nome_set_formatado;
	}
	public void setNome_set_formatado(String nome_set_formatado) {
		this.nome_set_formatado = nome_set_formatado;
	}
	 
	 
	
}
