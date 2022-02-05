package com.naicson.yugioh.util.enums;

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
