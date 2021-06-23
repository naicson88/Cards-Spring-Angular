package com.naicson.yugioh.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tab_cards")
public class Card extends Sets {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer numero;
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
	private Sets sets;
	private String generic_type;
	@Column(name = "cod_archetype")
	private int codArchetype;


	public Card() {
		super();
	}

	public Card(Integer id, Integer numero, String categoria, String nome, String nomePortgues, String atributo,
			String propriedade, Integer nivel, String tipos, Integer atk, Integer def, String condicao,
			String descricao, String imagem, String raridade, Integer escala, 
			String descr_pendulum, String arquetipo, String qtd_link, Sets sets, String generic_type) {
		super();
		this.id = id;
		this.numero = numero;
		this.categoria = categoria;
		this.nome = nome;
		this.nomePortugues = nomePortgues;
		this.atributo = atributo;
		this.propriedade = propriedade;
		this.nivel = nivel;
		this.tipos = tipos;
		this.atk = atk;
		this.def = def;
		this.condicao = condicao;
		this.descricao = descricao;
		this.imagem = imagem;
		this.raridade = raridade;
		this.escala = escala;
		this.descr_pendulum = descr_pendulum;
		this.arquetipo = arquetipo;
		this.qtd_link = qtd_link;
		this.sets = sets;
		this.generic_type = generic_type;
	}
	
	
	
	public String getGeneric_type() {
		return generic_type;
	}

	public void setGeneric_type(String generic_type) {
		this.generic_type = generic_type;
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

	public Sets getSets() {
		return sets;
	}

	public void setSets(Sets sets) {
		this.sets = sets;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
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
	
	
	
}
