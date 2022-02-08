package com.naicson.yugioh.entity;



import java.io.Serializable;
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

import com.naicson.yugioh.entity.sets.DeckUsers;

@Entity
@Table(name = "tab_decks")
public class Deck implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String imagem;
	private String nomePortugues;
	
	@Column(name = "qtd_cards")
	private Long qtd_cards;
	private Long qtd_comuns;
	private Long qtd_raras;
	private Long qtd_super_raras;
	private Long qtd_ultra_raras;
	//private Long qtd_secret_raras;
	
	private Long lancamento;
	
	@Column(name = "set_type")
	private String setType;
	@Column(name = "dt_criacao")
	private Date dt_criacao;
	
	@Transient
	private List<Card> cards;
	@Transient
	private List<Card> sideDeckCards;
	@Transient
	private List<Card> extraDeckCards;
	@Transient
	private List<RelDeckCards> rel_deck_cards;
		
	public Deck() {
		
	}
	
	public static Deck deckFromDeckUser(DeckUsers deckUser) {
		Deck deck = new Deck();
		deck.setId(deckUser.getId());
		deck.setNome(deckUser.getNome());
		deck.setImagem(deckUser.getImagem());
		return deck;
		
	}
	
	public List<Card> getExtraDeck() {
		return extraDeckCards;
	}

	public void setExtraDeck(List<Card> extraDeck) {
		this.extraDeckCards = extraDeck;
	}

	public List<Card> getSideDeckCards() {
		return sideDeckCards;
	}

	public void setSideDeckCards(List<Card> sideDeckCards) {
		this.sideDeckCards = sideDeckCards;
	}

	public List<RelDeckCards> getRel_deck_cards() {
		return rel_deck_cards;
	}

	public void setRel_deck_cards(List<RelDeckCards> rel_deck_cards) {
		this.rel_deck_cards = rel_deck_cards;
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

	public Long getQtd_cards() {
		return qtd_cards;
	}

	public void setQtd_cards(Long qtd_cards) {
		this.qtd_cards = qtd_cards;
	}

	public Long getQtd_comuns() {
		return qtd_comuns;
	}

	public void setQtd_comuns(Long qtd_comuns) {
		this.qtd_comuns = qtd_comuns;
	}

	public Long getQtd_raras() {
		return qtd_raras;
	}

	public void setQtd_raras(Long qtd_raras) {
		this.qtd_raras = qtd_raras;
	}

	public Long getQtd_super_raras() {
		return qtd_super_raras;
	}

	public void setQtd_super_raras(Long qtd_super_raras) {
		this.qtd_super_raras = qtd_super_raras;
	}

	public Long getQtd_ultra_raras() {
		return qtd_ultra_raras;
	}

	public void setQtd_ultra_raras(Long qtd_ulta_raras) {
		this.qtd_ultra_raras = qtd_ulta_raras;
	}

	public Long getLancamento() {
		return lancamento;
	}

	public void setLancamento(Long lancamento) {
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
				+ ", qtd_super_raras=" + qtd_super_raras + ", qtd_ulta_raras=" + qtd_ultra_raras + ", lancamento="
				+ lancamento + ", cards=" + cards + "]";
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}
		
}
