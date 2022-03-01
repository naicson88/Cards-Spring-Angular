package com.naicson.yugioh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_atributo")
public class Atributo {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String atributoImgPath;
	
	public Atributo() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAtributoImgPath() {
		return atributoImgPath;
	}

	public void setAtributoImgPath(String atributoImgPath) {
		this.atributoImgPath = atributoImgPath;
	}

	
}
