package com.naicson.yugioh.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public interface UtilitariosService {
	
  void InsertOnSets(Integer deck_id, Integer card_numero, String card_raridade, String card_set_code, String card_price) throws SQLException;
}
