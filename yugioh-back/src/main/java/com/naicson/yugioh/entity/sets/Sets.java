package com.naicson.yugioh.entity.sets;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.naicson.yugioh.entity.Deck;

public class Sets {
	@Transient
	protected List<Deck> set_decks;
	@Transient 
	protected List<Tin> set_tins;
	@Transient
	protected List<Box> set_box;
	
	public Sets() {
		
	}

	public Sets(List<Deck> set_decks, List<Tin> set_tins, List<Box> set_box) {
		super();
		this.set_decks = set_decks;
		this.set_tins = set_tins;
		this.set_box = set_box;
	}
	
	public List<Deck> getSet_decks() {
		return set_decks;
	}

	public void setSet_decks(List<Deck> set_decks) {
		this.set_decks = set_decks;
	}

	public List<Tin> getSet_tins() {
		return set_tins;
	}

	public void setSet_tins(List<Tin> set_tins) {
		this.set_tins = set_tins;
	}

	public List<Box> getSet_box() {
		return set_box;
	}

	public void setSet_box(List<Box> set_box) {
		this.set_box = set_box;
	}

	@Override
	public String toString() {
		return "Sets [set_decks=" + set_decks + ", set_tins=" + set_tins + ", set_box=" + set_box + "]";
	}

	
	
}
