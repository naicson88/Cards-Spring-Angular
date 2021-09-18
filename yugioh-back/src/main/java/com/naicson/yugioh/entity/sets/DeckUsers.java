package com.naicson.yugioh.entity.sets;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_deck_users")
public class DeckUsers {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String imagem;
	@Column(name = "konami_deck_copied")
	private Integer konamiDeckCopied;
	private Integer userId;
	private Date dtCriacao;
	private String setType;
	
	
	
	public DeckUsers(Long id, String nome, String imagem, Integer konamiDeckCopied, Integer userId, Date dtCriacao,
			String setType) {
		super();
		this.id = id;
		this.nome = nome;
		this.imagem = imagem;
		this.konamiDeckCopied = konamiDeckCopied;
		this.userId = userId;
		this.dtCriacao = dtCriacao;
		this.setType = setType;
	}

	public DeckUsers() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getKonamiDeckCopied() {
		return konamiDeckCopied;
	}
	public void setKonamiDeckCopied(Integer konamiDeckCopied) {
		this.konamiDeckCopied = konamiDeckCopied;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	
}
