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

import com.naicson.yugioh.entity.sets.DeckUsers;

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
	private Integer lancamento;
	@Transient
	private List<Card> cards;
	@Transient
	private List<Card> sideDeckCards;
	@Transient
	private List<Card> extraDeckCards;
	@Transient
	private List<RelDeckCards> rel_deck_cards;
	@Column(name = "is_konami_deck")
	private String isKonamiDeck;
	@Column(name = "set_type")
	private String setType;
	@Column(name = "dt_criacao")
	private Date dt_criacao;
	@Column(name = "copied_from_deck")
	private Integer copiedFromDeck;	
	@Column(name = "user_id")
	private Integer userId;
		
	public Deck() {
		
	}
	
	public Deck(Long id, String nome, String imagem, String nomePortugues, Integer qtd_cards, Integer qtd_comuns,
			Integer qtd_raras, Integer qtd_super_raras, Integer qtd_ulta_raras, Integer qtd_secret_raras,
			Integer lancamento, List<Card> cards, String card_set_code, Float card_price, String isKonamiDeck) {
		
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
		this.isKonamiDeck = isKonamiDeck;

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

	public Integer getLancamento() {
		return lancamento;
	}

	public void setLancamento(Integer lancamento) {
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

	public String getIsKonamiDeck() {
		return isKonamiDeck;
	}

	public void setIsKonamiDeck(String isKonamiDeck) {
		this.isKonamiDeck = isKonamiDeck;
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

	public Integer getCopiedFromDeck() {
		return copiedFromDeck;
	}

	public void setCopiedFromDeck(Integer copiedFromDeck) {
		 this.copiedFromDeck = copiedFromDeck;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
}
