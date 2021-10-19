package com.naicson.yugioh.dto.cards;

import java.util.Map;

public class CardAndSetsDTO {
	
	private long numero;
	private String nome;
	private String imagem;
	
	private Map<String, String> mapDeckSetcode;
	private Map<Long, String> mapSetsOfUser;
	
	public CardAndSetsDTO() {
		
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
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

	public Map<String, String> getMapDeckSetcode() {
		return mapDeckSetcode;
	}

	public void setMapDeckSetcode(Map<String, String> mapDeckSetcode) {
		this.mapDeckSetcode = mapDeckSetcode;
	}

	public Map<Long, String> getMapSetsOfUser() {
		return mapSetsOfUser;
	}

	public void setMapSetsOfUser(Map<Long, String> mapSetsOfUser) {
		this.mapSetsOfUser = mapSetsOfUser;
	}
	
	
}
