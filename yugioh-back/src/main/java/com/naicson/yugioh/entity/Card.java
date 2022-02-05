package com.naicson.yugioh.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.naicson.yugioh.entity.imgs.CardAttributeImage;


@Entity
@Table(name = "tab_cards")
public class Card {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Long numero;
	private String categoria;
	private String nome;
	private String nomePortugues;
	private String atributo;
	private String propriedade;
	private Integer nivel;
	private String tipos;
	private Integer atk;
	private Integer def;
	private String condicao;
	@Column(columnDefinition="text")
	private String descricao;
	@Column(columnDefinition="text")
	private String descricaoPortugues;
	private String imagem;
	private String raridade;
	private Integer escala;
	@Column(columnDefinition="text")
	private String descr_pendulum;
	@Column(columnDefinition="text")
	private String descr_pendulum_pt;
	private String arquetipo;
	private String qtd_link;
	@Transient
	private List<Deck> sets;
	@Column(name = "generic_type")
	private String genericType;
	@Column(name = "cod_archetype")
	private int codArchetype;
	@OneToOne(optional = false)
	@JoinColumn(name = "attribute_img_id", referencedColumnName = "id")
	private CardAttributeImage attributeImg;
	

	public Card() {
		
	}


	
	//Construtor para CardsSearchDTO
	public Card(Long numero, String nome, String imagem) {
		this.numero = numero;
		this.nome = nome;
		this.imagem = imagem;
	}
	
	public CardAttributeImage getAttributeImg() {
		return attributeImg;
	}

	public void setAttributeImg(CardAttributeImage attributeImg) {
		this.attributeImg = attributeImg;
	}

	public String getGeneric_type() {
		return genericType;
	}

	public void setGeneric_type(String generic_type) {
		this.genericType = generic_type;
	}

	public String getNomePortugues() {
		return nomePortugues;
	}

	public void setNomePortugues(String nomePortugues) {
		this.nomePortugues = nomePortugues;
	}

	public String getDescricaoPortugues() {
		return descricaoPortugues;
	}

	public void setDescricaoPortugues(String descricaoPortugues) {
		this.descricaoPortugues = descricaoPortugues;
	}

	public String getDescr_pendulum_pt() {
		return descr_pendulum_pt;
	}

	public void setDescr_pendulum_pt(String descr_pendulum_pt) {
		this.descr_pendulum_pt = descr_pendulum_pt;
	}

	public List<Deck> getSets() {
		return sets;
	}

	public void setSets(List<Deck> deck_set) {
		this.sets = deck_set;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getTipos() {
		return tipos;
	}

	public void setTipos(String tipos) {
		this.tipos = tipos;
	}

	public Integer getAtk() {
		return atk;
	}

	public void setAtk(Integer atk) {
		this.atk = atk;
	}

	public Integer getDef() {
		return def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getRaridade() {
		return raridade;
	}

	public void setRaridade(String raridade) {
		this.raridade = raridade;
	}

	public Integer getEscala() {
		return escala;
	}

	public void setEscala(Integer escala) {
		this.escala = escala;
	}

	public String getDescr_pendulum() {
		return descr_pendulum;
	}

	public void setDescr_pendulum(String descr_pendulum) {
		this.descr_pendulum = descr_pendulum;
	}

	public String getArquetipo() {
		return arquetipo;
	}

	public void setArquetipo(String arquetipo) {
		this.arquetipo = arquetipo;
	}

	public String getQtd_link() {
		return qtd_link;
	}

	public void setQtd_link(String qtd_link) {
		this.qtd_link = qtd_link;
	}

	public String getNomePortgues() {
		return nomePortugues;
	}

	public void setNomePortgues(String nomePortgues) {
		this.nomePortugues = nomePortgues;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	public int getCodArchetype() {
		return codArchetype;
	}

	public void setCodArchetype(int codArchetype) {
		this.codArchetype = codArchetype;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", numero=" + numero + ", categoria=" + categoria + ", nome=" + nome
				+ ", nomePortugues=" + nomePortugues + ", atributo=" + atributo + ", propriedade=" + propriedade
				+ ", nivel=" + nivel + ", tipos=" + tipos + ", atk=" + atk + ", def=" + def + ", condicao=" + condicao
				+ ", descricao=" + descricao + ", descricaoPortugues=" + descricaoPortugues + ", imagem=" + imagem
				+ ", raridade=" + raridade + ", escala=" + escala + ", descr_pendulum=" + descr_pendulum
				+ ", descr_pendulum_pt=" + descr_pendulum_pt + ", arquetipo=" + arquetipo + ", qtd_link=" + qtd_link
				+ ", sets=" + sets + ", generic_type=" + genericType + ", codArchetype=" + codArchetype + "]";
	}
	
	
	
}
