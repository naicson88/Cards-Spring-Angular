package com.naicson.yugioh.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tab_rel_user_cards")
@Entity
public class RelUserCardsDTO {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private long userId;
	private Integer cardNumero;
	private String cardSetCode;
	private Integer qtd;
	private Date dtCriacao;
	
	
	public RelUserCardsDTO() {
		
	}
	

	public RelUserCardsDTO(Integer id, Integer userId, Integer cardNumero, String cardSetCode, Integer qtd) {
		super();
		this.id = id;
		this.userId = userId;
		this.cardNumero = cardNumero;
		this.cardSetCode = cardSetCode;
		this.qtd = qtd;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Integer getCardNumero() {
		return cardNumero;
	}
	public void setCardNumero(Integer cardNumero) {
		this.cardNumero = cardNumero;
	}
	public String getCardSetCode() {
		return cardSetCode;
	}
	public void setCardSetCode(String cardSetCode) {
		this.cardSetCode = cardSetCode;
	}
	public Integer getQtd() {
		return qtd;
	}
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}


	public Date getDtCriacao() {
		return dtCriacao;
	}


	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	
	
}
