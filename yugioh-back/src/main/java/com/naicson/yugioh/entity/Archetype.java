package com.naicson.yugioh.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.naicson.yugioh.dto.cards.CardOfArchetypeDTO;

@Entity
@Table(name = "tab_archetypes")
public class Archetype {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "arc_name")
	private String arcName;
	
	@Transient
	private List<CardOfArchetypeDTO> arrayCards;
	@Transient
	private List<Deck>arrayDecks;

	public Archetype() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArcName() {
		return arcName;
	}

	public void setArcName(String arcName) {
		this.arcName = arcName;
	}

	public List<CardOfArchetypeDTO> getArrayCards() {
		return arrayCards;
	}

	public void setArrayCards(List<CardOfArchetypeDTO> arrayCards) {
		this.arrayCards = arrayCards;
	}

	public List<Deck> getArrayDecks() {
		return arrayDecks;
	}

	public void setArrayDecks(List<Deck> arrayDecks) {
		this.arrayDecks = arrayDecks;
	}

	@Override
	public String toString() {
		return "Archetype [id=" + id + ", arcName=" + arcName + ", arrayCards=" + arrayCards + ", arrayDecks="
				+ arrayDecks + "]";
	}
	
}
