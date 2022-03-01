package com.naicson.yugioh.service.interfaces;

import java.util.List;

import com.naicson.yugioh.entity.RelDeckCards;

public interface RelDeckCardsDetails {
	
	List<RelDeckCards> saveRelDeckCards(List<RelDeckCards> list) throws Exception;
}
