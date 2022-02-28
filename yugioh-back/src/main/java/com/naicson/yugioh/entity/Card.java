package com.naicson.yugioh.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
	@ManyToOne
	@JoinColumn(name = "atributo_id",  referencedColumnName = "id")
	private Atributo atributo;
	private String propriedade;
	private Integer nivel;
	@ManyToOne
	@JoinColumn(name = "tipo_card_id",  referencedColumnName = "id")
	private TipoCard tipo;
	private Integer atk;
	private Integer def;
	@Column(columnDefinition="text")
	private String descricao;
	@Column(columnDefinition="text")
	private String descricaoPortugues;
	private String imagem;
	private Integer escala;
	@Column(columnDefinition="text")
	private String descr_pendulum;
	@Column(columnDefinition="text")
	private String descr_pendulum_pt;
	private String qtd_link;
	@Transient
	private List<Deck> sets;
	@Column(name = "generic_type")
	private String genericType;
	@ManyToOne
	@JoinColumn(name = "cod_archetype",  referencedColumnName = "id")
	private Archetype archetype;

	private Date registryDate;
	
	public Card() {
		
	}

	//Construtor para CardsSearchDTO
	public Card(Long numero, String nome, String imagem) {
		this.numero = numero;
		this.nome = nome;
		this.imagem = imagem;
	}


	public Atributo getAtributo() {
		return atributo;
	}



	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}



	public String getGenericType() {
		return genericType;
	}



	public void setGenericType(String genericType) {
		this.genericType = genericType;
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
	
	public TipoCard getTipo() {
		return tipo;
	}

	public void setTipo(TipoCard tipo) {
		this.tipo = tipo;
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

	public Archetype getArchetype() {
		return archetype;
	}

	public void setArchetype(Archetype archetype) {
		this.archetype = archetype;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
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

	@Override
	public String toString() {
		return "Card [id=" + id + ", numero=" + numero + ", categoria=" + categoria + ", nome=" + nome
				+ ", nomePortugues=" + nomePortugues + ", atributo=" + atributo + ", propriedade=" + propriedade
				+ ", nivel=" + nivel + ", tipo=" + tipo + ", atk=" + atk + ", def=" + def + ", descricao=" + descricao
				+ ", descricaoPortugues=" + descricaoPortugues + ", imagem=" + imagem + ", escala=" + escala
				+ ", descr_pendulum=" + descr_pendulum + ", descr_pendulum_pt=" + descr_pendulum_pt + ", qtd_link="
				+ qtd_link + ", sets=" + sets + ", genericType=" + genericType + ", archetype=" + archetype
				+ ", registryDate=" + registryDate + "]";
	}
	
	
	
	
}
