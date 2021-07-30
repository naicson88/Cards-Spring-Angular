package com.naicson.yugioh.util;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.service.DeckServiceImpl;

@Repository
public  class AdicionarCardsNoSet {
	
	 private static final String NOME_SET = "Starter Deck: Yuya";
	 private static final Integer DECK_ID = 8;
	 private static Integer card_numero;
	 private static String card_raridade;
	 private static String card_set_code;
	 private static String card_price;
	 final static String URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?set=";
	
	String[] itens = {"a", "b"};
	 
	@Autowired
	 EntityManager em;
	
	//@EventListener(ContextRefreshedEvent.class)	

	public  void InserirNosSets() {
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(URL + "Starter%20Deck:%20Yuya");
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			
			try {
				JSONObject objeto = new JSONObject(json);
				JSONArray jsonArray = objeto.getJSONArray("data");
				
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject objetoNoArray = jsonArray.getJSONObject(i);
					card_numero = (Integer) objetoNoArray.get("id");
					
					JSONArray arrayCardSet = objetoNoArray.getJSONArray("card_sets");
					
					for(int j = 0; j < arrayCardSet.length(); j++) {
						JSONObject objCardSet = arrayCardSet.getJSONObject(j);
						String nomeSet = (String) objCardSet.get("set_name");
						
						if(nomeSet.equals(NOME_SET)) {
							card_raridade = (String) objCardSet.get("set_rarity");
							card_set_code = (String) objCardSet.get("set_code");
							card_price = (String) 	 objCardSet.get("set_price");
							// InsertOnSets(DECK_ID, card_numero, card_raridade, card_set_code, card_price);
							//utilController.InsertOnSets(DECK_ID, card_numero, card_raridade, card_set_code, card_price);
							DeckServiceImpl impl = new DeckServiceImpl();
							System.out.println(em);
							impl.InsertOnSets(DECK_ID, card_numero, card_raridade, card_set_code, card_price);
							System.out.println( card_numero + ", " + card_price +", "+ card_set_code+", " + card_raridade);
							
						}
					}				
				}			
				
			}catch(Exception e) {
				e.getMessage();
			}
			
		} catch(IOException e) {
			e.getStackTrace();
		}
	}
	
}

