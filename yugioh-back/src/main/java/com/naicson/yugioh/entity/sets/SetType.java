package com.naicson.yugioh.entity.sets;

public enum SetType {
	DECK("D"),
	TIN("T"),
	BOX("B");
	
	private final String type;
	
	SetType(String typeOption) {
		type = typeOption;
	}
	
	public String getType() {
		return type;
	}
}
