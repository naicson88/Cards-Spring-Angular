package com.naicson.yugioh.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class CardYuGiOhAPI implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String categoria;
	private String name;
	private String attribute;
	private Integer level;
	private String race;
	private Integer atk;
	private Integer def;
	private String desc;
	private String archetype;
	private String scale;
	private int linkval;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return categoria;
	}

	public void setType(String type) {
		this.categoria = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getArchetype() {
		return archetype;
	}

	public void setArchetype(String archetype) {
		this.archetype = archetype;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public int getLinkval() {
		return linkval;
	}

	public void setLinkval(int linkval) {
		this.linkval = linkval;
	}
	
}