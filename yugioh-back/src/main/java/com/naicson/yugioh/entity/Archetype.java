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

@Entity
@Table(name = "tab_archetypes")
public class Archetype {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "arc_name")
	private String arcName;
	
	@Transient
	private List<Card> arrayCards;
	@Transient
	private List<Deck>arrayDecks;

	public Archetype() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArcName() {
		return arcName;
	}

	public void setArcName(String arcName) {
		this.arcName = arcName;
	}

	public List<Card> getArrayCards() {
		return arrayCards;
	}

	public void setArrayCards(List<Card> arrayCards) {
		this.arrayCards = arrayCards;
	}

	public List<Deck> getArrayDecks() {
		return arrayDecks;
	}

	public void setArrayDecks(List<Deck> arrayDecks) {
		this.arrayDecks = arrayDecks;
	}
	
	



	
}
