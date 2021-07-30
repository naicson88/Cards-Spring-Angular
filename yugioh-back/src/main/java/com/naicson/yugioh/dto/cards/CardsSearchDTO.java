package com.naicson.yugioh.dto.cards;

import com.naicson.yugioh.entity.Card;

public class CardsSearchDTO {
	
	private Integer numero;
	private String nome;
	private String imagem;
	
	public CardsSearchDTO() {
		
	}	
	
	public CardsSearchDTO(Integer numero, String nome, String imagem) {
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
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
	
	
}
