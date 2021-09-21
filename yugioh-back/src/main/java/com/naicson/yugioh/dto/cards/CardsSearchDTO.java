package com.naicson.yugioh.dto.cards;

import java.util.List;

import javax.persistence.Transient;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;

public class CardsSearchDTO {
	
	private Long numero;
	private String nome;
	private String imagem;
	@Transient
	private List<Deck> decksWithThisCard;
	
	public CardsSearchDTO() {
		
	}	
	
	public CardsSearchDTO(Long numero, String nome, String imagem) {
		super();
		this.numero = numero;
		this.nome = nome;
		this.imagem = imagem;
	}

	public static CardsSearchDTO transformInDTO(Card card) {
		return new CardsSearchDTO(card.getNumero(), card.getNome(), card.getImagem());
	}
	
	public Card transformInObject() {
		return new Card(numero, nome, imagem);
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<Deck> getDecksWithThisCard() {
		return decksWithThisCard;
	}

	public void setDecksWithThisCard(List<Deck> decksWithThisCard) {
		this.decksWithThisCard = decksWithThisCard;
	}
	
	
}
