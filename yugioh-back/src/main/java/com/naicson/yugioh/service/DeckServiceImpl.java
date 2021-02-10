package com.naicson.yugioh.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.QueryHint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.entity.Deck;

@Service
public class DeckServiceImpl implements DeckDetailService {
	
	@Autowired
	 EntityManager em;
	
	public List<Deck> contasQtdCardsPorRaridade(Long deckId){
		
		Query query = em.createNativeQuery("SELECT distinct deck.id,deck.imagem,deck.nome,deck.nome_portugues, total.qtd_total, comuns.qtd_comuns, raros.qtd_raros, sraros.qtd_sraros, uraros.qtd_uraros\r\n" + 
				"from  tab_decks as deck\r\n" + 
				"inner join tab_rel_deck_cards rel on rel.deck_id = deck.id \r\n" + 
				"left join (SELECT COUNT(card_id) as qtd_total, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId) \r\n" + 
				"as total on total.deck_id = deck.id\r\n" + 
				"left join (SELECT COUNT(RARIDADE) as qtd_comuns, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId AND raridade = 'common') \r\n" + 
				"as comuns on comuns.deck_id = deck.id\r\n" + 
				"left join (SELECT COUNT(RARIDADE) as qtd_raros, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId AND raridade = 'Rare')\r\n" + 
				" as raros on raros.deck_id = deck.id\r\n" + 
				"left join (SELECT COUNT(RARIDADE) as qtd_sraros, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId AND raridade = 'Super Rare')\r\n" + 
				" as sraros on sraros.deck_id = deck.id\r\n" + 
				" left join (SELECT COUNT(RARIDADE) as qtd_uraros, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId AND raridade = 'Ultra Rare')\r\n" + 
				" as uraros on uraros.deck_id = deck.id\r\n" + 
				"where rel.deck_id = :deckId");
				
				List<Deck> deck =  query.setParameter("deckId", deckId).getResultList();
				return deck;			
	}
	
	/*
	public List<Deck> countNumberOfCards(Long deckId) {	
		
		Query query = em.createNativeQuery("SELECT count(rel.card_id) qtd_total, comuns.qtd_comuns, raros.qtd_raros, sraros.qtd_sraros, uraros.qtd_uraros\r\n" + 
				" from tab_decks as deck\r\n" + 
				"inner join tab_rel_deck_cards rel on rel.deck_id = deck.id \r\n" + 
				"left join (SELECT COUNT(RARIDADE) as qtd_comuns, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId AND raridade = 'common') \r\n" + 
				"as comuns on comuns.deck_id = deck.id\r\n" + 
				"left join (SELECT COUNT(RARIDADE) as qtd_raros, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId  AND raridade = 'Rare')\r\n" + 
				" as raros on raros.deck_id = deck.id\r\n" + 
				"left join (SELECT COUNT(RARIDADE) as qtd_sraros, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId  AND raridade = 'Super Rare')\r\n" + 
				" as sraros on sraros.deck_id = deck.id\r\n" + 
				" left join (SELECT COUNT(RARIDADE) as qtd_uraros, deck_id FROM tab_rel_deck_cards WHERE DECK_ID = :deckId  AND raridade = 'Ultra Rare')\r\n" + 
				" as uraros on uraros.deck_id = deck.id\r\n" + 
				"where rel.deck_id = :deckId ");
				 
			  List<Deck> deckList = query.setParameter("deckId", deckId).getResultList();
			  
			  return deckList;		
	} */ 

	@Override
	public List<Deck> findByNomeContaining(String nomeDeck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Deck> countNumberOfCards(Integer deckId) {
		// TODO Auto-generated method stub
		return null;
	}
}
