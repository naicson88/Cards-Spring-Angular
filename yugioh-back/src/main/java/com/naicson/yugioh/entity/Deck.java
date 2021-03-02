package com.naicson.yugioh.entity;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tab_decks")
public class Deck {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String imagem;
	private String nomePortugues;
	@Column(name = "qtd_cards")
	private Integer qtd_cards;
	private Integer qtd_comuns;
	private Integer qtd_raras;
	private Integer qtd_super_raras;
	private Integer qtd_ulta_raras;
	private Integer qtd_secret_raras;
	private Date lancamento;
	@Transient
	private List<Card> cards;
	@Transient
	private String card_set_code;
	@Transient
	private Float card_price;
	
	public Deck() {
		
	}
	
	public Deck(Long id, String nome, String imagem, String nomePortugues, Integer qtd_cards, Integer qtd_comuns,
			Integer qtd_raras, Integer qtd_super_raras, Integer qtd_ulta_raras, Integer qtd_secret_raras,
			Date lancamento, List<Card> cards, String card_set_code, Float card_price) {
		super();
		this.id = id;
		this.nome = nome;
		this.imagem = imagem;
		this.nomePortugues = nomePortugues;
		this.qtd_cards = qtd_cards;
		this.qtd_comuns = qtd_comuns;
		this.qtd_raras = qtd_raras;
		this.qtd_super_raras = qtd_super_raras;
		this.qtd_ulta_raras = qtd_ulta_raras;
		this.qtd_secret_raras = qtd_secret_raras;
		this.lancamento = lancamento;
		this.cards = cards;
		this.card_set_code = card_set_code;
		this.card_price = card_price;
	}


	public String getCard_set_code() {
		return card_set_code;
	}

	public void setCard_set_code(String card_set_code) {
		this.card_set_code = card_set_code;
	}

	public Float getCard_price() {
		return card_price;
	}

	public void setCard_price(Float card_price) {
		this.card_price = card_price;
	}

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

	public String getNomePortugues() {
		return nomePortugues;
	}

	public void setNomePortugues(String nomePortugues) {
		this.nomePortugues = nomePortugues;
	}

	public Integer getQtd_cards() {
		return qtd_cards;
	}

	public void setQtd_cards(Integer qtd_cards) {
		this.qtd_cards = qtd_cards;
	}

	public Integer getQtd_comuns() {
		return qtd_comuns;
	}

	public void setQtd_comuns(Integer qtd_comuns) {
		this.qtd_comuns = qtd_comuns;
	}

	public Integer getQtd_raras() {
		return qtd_raras;
	}

	public void setQtd_raras(Integer qtd_raras) {
		this.qtd_raras = qtd_raras;
	}

	public Integer getQtd_super_raras() {
		return qtd_super_raras;
	}

	public void setQtd_super_raras(Integer qtd_super_raras) {
		this.qtd_super_raras = qtd_super_raras;
	}

	public Integer getQtd_ulta_raras() {
		return qtd_ulta_raras;
	}

	public void setQtd_ulta_raras(Integer qtd_ulta_raras) {
		this.qtd_ulta_raras = qtd_ulta_raras;
	}

	public Date getLancamento() {
		return lancamento;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}

	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}



	@Override
	public String toString() {
		return "Deck [id=" + id + ", nome=" + nome + ", imagem=" + imagem + ", nomePortugues=" + nomePortugues
				+ ", qtd_cards=" + qtd_cards + ", qtd_comuns=" + qtd_comuns + ", qtd_raras=" + qtd_raras
				+ ", qtd_super_raras=" + qtd_super_raras + ", qtd_ulta_raras=" + qtd_ulta_raras + ", lancamento="
				+ lancamento + ", cards=" + cards + "]";
	}



	public Integer getQtd_secret_raras() {
		return qtd_secret_raras;
	}



	public void setQtd_secret_raras(Integer qtd_secret_raras) {
		this.qtd_secret_raras = qtd_secret_raras;
	}

	
}
