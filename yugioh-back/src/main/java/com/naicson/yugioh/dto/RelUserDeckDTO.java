package com.naicson.yugioh.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tab_rel_user_deck")
@Entity
public class RelUserDeckDTO {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private Integer deckId;
	private Integer qtd;
	
	public RelUserDeckDTO() {
		
	}
	
	public RelUserDeckDTO(Integer id, Integer userId, Integer deckId, Integer quantity) {
		super();
		this.id = id;
		this.userId = userId;
		this.deckId = deckId;
		this.qtd = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDeckId() {
		return deckId;
	}

	public void setDeckId(Integer deckId) {
		this.deckId = deckId;
	}

	public Integer getQuantity() {
		return qtd;
	}

	public void setQuantity(Integer quantity) {
		this.qtd = quantity;
	}	
	
}
