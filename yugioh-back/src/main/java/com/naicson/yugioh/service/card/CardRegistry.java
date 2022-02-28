package com.naicson.yugioh.service.card;

import java.util.Date;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.CardYuGiOhAPI;
import com.naicson.yugioh.dto.KonamiDeck;
import com.naicson.yugioh.entity.Archetype;
import com.naicson.yugioh.entity.Atributo;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.TipoCard;
import com.naicson.yugioh.repository.ArchetypeRepository;
import com.naicson.yugioh.repository.AtributoRepository;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.repository.TipoCardRepository;
import com.naicson.yugioh.util.GeneralFunctions;
import com.naicson.yugioh.util.enums.CardProperty;
import com.naicson.yugioh.util.enums.GenericTypesCards;


@Service
public class CardRegistry {
	
	@Autowired
	CardRepository cardRepository;
	@Autowired
	TipoCardRepository cardTypeRepository;
	@Autowired
	ArchetypeRepository archRepository;
	@Autowired
	AtributoRepository attRepository;
	
	Logger logger = LoggerFactory.getLogger(CardRegistry.class);
	
	public void RegistryCardFromYuGiOhAPI(KonamiDeck kDeck) {
		
		if(kDeck == null) {
			logger.error("Invalid Konami Deck");
			throw new IllegalArgumentException("Invalid Konami Deck.");
		}
		
		if(kDeck.getCardsToBeRegistered() != null && kDeck.getCardsToBeRegistered().size() > 0) {
			
			kDeck.getCardsToBeRegistered().stream().forEach(apiCard -> {
			
			Card cardToBeRegistered = new Card();
			//Informations that all cards may have
			cardToBeRegistered.setNumero(apiCard.getId());
			cardToBeRegistered.setCategoria(apiCard.getType());
			cardToBeRegistered.setNome(apiCard.getName());
			cardToBeRegistered.setRegistryDate(new Date());	
			cardToBeRegistered.setArchetype(apiCard.getRace() != null ? this.getCardArchetype(apiCard.getArchetype()) : null);			
			cardToBeRegistered.setAtributo(apiCard.getAttribute() != null ? this.getCardAttribute(apiCard.getAttribute()) : null);
					
				
			if(StringUtils.containsIgnoreCase("spell", apiCard.getType()) || 
				StringUtils.containsIgnoreCase("trap", apiCard.getType()) ||
				StringUtils.containsIgnoreCase("token", apiCard.getType()) ) {
				
			   cardToBeRegistered.setPropriedade(apiCard.getRace() != null ? this.getCardProperty(apiCard.getRace()) : null);
					
				} else {
					
				cardToBeRegistered.setNivel(apiCard.getLevel());
				cardToBeRegistered.setAtk(apiCard.getAtk());
				cardToBeRegistered.setDef(apiCard.getDef());
				cardToBeRegistered.setQtd_link(apiCard.getLinkval() > 0 ? String.valueOf(apiCard.getLinkval()): null); //validar
				cardToBeRegistered.setEscala(apiCard.getScale() != null ? Integer.parseInt(apiCard.getScale()) : null); //validar				
			    cardToBeRegistered.setTipo(apiCard.getRace() != null ? this.getCardType(apiCard.getRace()) : null);
					
				}
				
				cardToBeRegistered.setGenericType(this.setCardGenericType(apiCard.getType()));
				
			    cardToBeRegistered = this.setCardDesc(cardToBeRegistered, apiCard); 
			    
			    GeneralFunctions.saveCardInFolder(cardToBeRegistered.getNumero());
			    
			    this.validCardBeforeSave(cardToBeRegistered);
			    
			    Card cardSaved = cardRepository.save(cardToBeRegistered);
			    
			    if(cardSaved == null || cardSaved.getId() == null) {
			    	logger.error("It was not possible save the card: " + cardToBeRegistered.getNumero());
			    	throw new IllegalArgumentException("It was not possible save the card: " + cardToBeRegistered.getNumero());
			    } else {
			    	logger.info("Card successfuly saved! {}", cardSaved.toString());
			    }
					
			});			
		}		
	}

	private void validCardBeforeSave(Card cardToBeRegistered) {
		
		if(cardToBeRegistered.getNumero() == null || cardToBeRegistered.getNumero() == 0) 
			throw new IllegalArgumentException("Invalid card number.");
		
		if(cardToBeRegistered.getCategoria() == null || cardToBeRegistered.getCategoria().isEmpty())
			throw new IllegalArgumentException("Invalid Card category.");
		
		if(cardToBeRegistered.getNome() == null || cardToBeRegistered.getNome().isEmpty())
			throw new IllegalArgumentException("Invalid card name.");
		
		if(StringUtils.containsIgnoreCase(cardToBeRegistered.getCategoria(), "link")) {
			if(cardToBeRegistered.getQtd_link() == null || cardToBeRegistered.getQtd_link().equals("0"))
				throw new IllegalArgumentException("Invalid Link Quantity.");
		} 
		
		if(StringUtils.containsIgnoreCase(cardToBeRegistered.getCategoria(), "pendulum")) {
			if(cardToBeRegistered.getEscala() == null || cardToBeRegistered.getEscala() == 0)
				throw new IllegalArgumentException("Invalid Card Scale.");
		} 
		
	}

	private Card setCardDesc(Card cardToBeRegistered, CardYuGiOhAPI apiCard) {
		
		if(StringUtils.containsIgnoreCase("pendulum", apiCard.getType())) {
			
		   	String fullDesc = apiCard.getDesc();
		   	//Verify if has pendulum description
		   	if(fullDesc.contains("----------------------------------------")) {
		   		String[] parts = fullDesc.split("----------------------------------------");
			   	String pendulumDesc = parts[0];
			   	String simpleDesc = parts[1];
				cardToBeRegistered.setDescricao(simpleDesc);
				cardToBeRegistered.setDescr_pendulum(pendulumDesc);
		   	} else {
		   		cardToBeRegistered.setDescricao(fullDesc);
		   	}

		} else {
		   cardToBeRegistered.setDescricao(apiCard.getDesc());   
		}
		
		return cardToBeRegistered;
	}

	private String setCardGenericType(String type) {
		String generic = null;
		
		if(StringUtils.containsIgnoreCase(type, "xyz"))
			generic = GenericTypesCards.XYZ.toString();
		else if(StringUtils.containsIgnoreCase("fusion", type))
			generic = GenericTypesCards.FUSION.toString();
		else if(StringUtils.containsIgnoreCase("link", type))
			generic = GenericTypesCards.LINK.toString();
		else if(StringUtils.containsIgnoreCase("pendulum", type))
			generic = GenericTypesCards.PENDULUM.toString();
		else if(StringUtils.containsIgnoreCase("spell", type))
			generic = GenericTypesCards.SPELL.toString();
		else if(StringUtils.containsIgnoreCase("trap", type))
			generic = GenericTypesCards.TRAP.toString();
		else if(StringUtils.containsIgnoreCase("synchro", type))
			generic = GenericTypesCards.SYNCHRO.toString();
		else if(StringUtils.containsIgnoreCase(type, "ritual"))
			generic = GenericTypesCards.RITUAL.toString();
		else if(StringUtils.containsIgnoreCase("token", type))
			generic = GenericTypesCards.TOKEN.toString();
		else if(StringUtils.containsIgnoreCase("monster", type))
			generic = GenericTypesCards.MONSTER.toString();
		else 
			throw new IllegalArgumentException("Invalid monster type");
		
		return generic;
	}
	
	private String getCardProperty(String race) {
		if(!EnumUtils.isValidEnum(CardProperty.class, race))
				throw new IllegalArgumentException("Invalid property");
		
		return race;
	}

	private Atributo getCardAttribute(String attribute) {
		Atributo att = attRepository.findByName(attribute.trim());
		
		if(att == null) {
			throw new EntityNotFoundException("Cant found Attribute: " + attribute);
		}
		
		return att;
	}

	private Archetype getCardArchetype(String archetype) {
		Archetype arch = archRepository.findByArcName(archetype.trim());
		
		if(arch == null) {
			throw new EntityNotFoundException("Cant found Archetype: " + archetype);
		}
		
		return arch;
	}

	private TipoCard getCardType(String race) {
		TipoCard type = cardTypeRepository.findByName(race.trim());
		
		if(type == null) {
			throw new EntityNotFoundException("Cant found Card Type with name: " + race);
		}
		
		return type;
	}
	
	 
}
