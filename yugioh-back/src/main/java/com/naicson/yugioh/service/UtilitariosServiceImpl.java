package com.naicson.yugioh.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.controller.CardController;
import com.naicson.yugioh.controller.DeckController;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.service.interfaces.UtilitariosService;

@Service
public class UtilitariosServiceImpl implements UtilitariosService {
	
	@Autowired
	EntityManager em;
	
	@Autowired
	CardController cardController;
	
	@Transactional
	public int adicionarCardDeck(String nome_set, String nome_set_formatado, Integer deck_id, String URL) throws SQLException {
		//Traz a quantidade total de cards inseridos
		int count = 0;
		
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(URL + nome_set_formatado);
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			try {
				JSONObject objeto = new JSONObject(json);
				JSONArray jsonArray = objeto.getJSONArray("data");
				
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject objetoNoArray = jsonArray.getJSONObject(i);
					Integer card_numero = (Integer) objetoNoArray.get("id");
					
					JSONArray arrayCardSet = objetoNoArray.getJSONArray("card_sets");
					
					for(int j = 0; j < arrayCardSet.length(); j++) {
						JSONObject objCardSet = arrayCardSet.getJSONObject(j);
						String nomeSet = (String) objCardSet.get("set_name");
						
						if(nomeSet.equals(nome_set)) {
							String card_raridade = (String) objCardSet.get("set_rarity");
							String card_set_code = (String) objCardSet.get("set_code");
							String card_price = (String) 	 objCardSet.get("set_price");
							// InsertOnSets(DECK_ID, card_numero, card_raridade, card_set_code, card_price);
							//utilController.InsertOnSets(DECK_ID, card_numero, card_raridade, card_set_code, card_price);
							DeckServiceImpl impl = new DeckServiceImpl();
							InsertOnSets(deck_id, card_numero, card_raridade, card_set_code, card_price);
							count++;
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
		return count;
	}
	
	@Override
	@Transactional
	public void InsertOnSets(Integer deck_id, Integer card_numero, String card_raridade, String card_set_code,
			String card_price) throws SQLException {
		Query query = null;
		 try {
			 query = em.createNativeQuery("INSERT INTO tab_rel_deck_cards (deck_id, card_numero, card_raridade, card_set_code, card_price) VALUES (:deck_id,:card_numero,:card_raridade,:card_set_code,:card_price)")
					.setParameter("deck_id", deck_id)
					.setParameter("card_numero", card_numero)
					.setParameter("card_raridade", card_raridade)
					.setParameter("card_set_code", card_set_code)
					.setParameter("card_price",  Double.parseDouble(card_price));
			 		int qtd = query.executeUpdate();	
			 		
		}catch(RuntimeException e) {
			e.getMessage();
			System.out.println(card_numero);
		}	 
	}
	
	
	@Transactional
	public int adicionarCardsTAB_CARDS(Long card_numero, String imagem_url, boolean isPt, String URL) throws SQLException {
		int count = 0;
		
		try {
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(URL);
			HttpGet request_pt = new HttpGet(URL + "&language=pt");
			
			HttpResponse response;
			
			if(isPt == false) {
				request.addHeader("accept", "application/json");
				response = client.execute(request);
			} else {
				request_pt.addHeader("accept", "application/json");
				response = client.execute(request_pt);
			}
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			try {
				
				JSONObject objeto = new JSONObject(json);
				JSONArray jsonArray = objeto.getJSONArray("data");
				
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					
					
					
					if(isPt == false) {	
						Card card = new Card();
						
						card.setNumero((Long)    	obj.get("id"));
						card.setNome((String) 		obj.get("name"));
						card.setCategoria((String)  obj.get("type"));
						card.setDescricao((String)  obj.get("desc"));
						card.setAtk((Integer) 		obj.get("atk"));
						card.setDef((Integer) 		obj.get("def"));
						card.setNivel((Integer)		obj.get("level"));
						//card.setTipos((String)		obj.get("race"));
						//card.setAtributo((String)	obj.get("attribute"));
						if(obj.has("archetype"))
						//card.setArquetipo((String)  obj.get("archetype"));
						if(obj.has("linkval"))
						card.setQtd_link((String)   obj.get("linkval"));
						if(obj.has("scanel"))
						card.setEscala((Integer)    obj.get("scale"));
						card.setImagem(imagem_url);
										
						cardController.adicionar(card);
						count++;

					} else {
						Card card = cardController.listarNumero(card_numero);
						card.setNomePortugues((String) 	obj.get("name"));
						card.setDescricaoPortugues((String) obj.get("desc"));
						
						cardController.editar(card, card.getId());
						count++;
					}			
				}		
			}catch (Exception e){
				e.getMessage();
			}
			
		}catch (Exception e){
			e.getMessage();
		}
		
		return count;
	}
}
