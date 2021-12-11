package com.naicson.yugioh.entity.imgs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_attribute_img")
public class CardAttributeImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private CardAttributes attribute_name;
	private String path_img;
	
	
	public CardAttributeImage() {
		
	}


	public CardAttributeImage(Long id, CardAttributes attribute_name, String path_img) {
		super();
		this.id = id;
		this.attribute_name = attribute_name;
		this.path_img = path_img;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public CardAttributes getAttribute_name() {
		return attribute_name;
	}


	public void setAttribute_name(CardAttributes attribute_name) {
		this.attribute_name = attribute_name;
	}


	public String getPath_img() {
		return path_img;
	}


	public void setPath_img(String path_img) {
		this.path_img = path_img;
	}
	
	
	
}
