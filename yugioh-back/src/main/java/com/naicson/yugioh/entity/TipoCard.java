package com.naicson.yugioh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_tipo_card")
public class TipoCard {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String tipoCardImgPath;
	
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
	public String getTipoCardImgPath() {
		return tipoCardImgPath;
	}
	public void setTipoCardImgPath(String tipoCardImgPath) {
		this.tipoCardImgPath = tipoCardImgPath;
	}
	

	
	
}
