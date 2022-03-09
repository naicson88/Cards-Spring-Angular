package com.naicson.yugioh.service.card;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dao.CardDAO;
import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.cards.CardAndSetsDTO;
import com.naicson.yugioh.dto.cards.CardDetailsDTO;
import com.naicson.yugioh.dto.cards.CardOfArchetypeDTO;
import com.naicson.yugioh.dto.cards.CardOfUserDetailDTO;
import com.naicson.yugioh.dto.cards.CardsSearchDTO;
import com.naicson.yugioh.dto.set.CardsOfUserSetsDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.CardAlternativeNumber;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.repository.CardAlternativeNumberRepository;
import com.naicson.yugioh.repository.CardRepository;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.service.HomeServiceImpl;
import com.naicson.yugioh.service.UserDetailsImpl;
import com.naicson.yugioh.service.interfaces.CardDetailService;
import com.naicson.yugioh.util.GeneralFunctions;
import com.naicson.yugioh.util.exceptions.ApiExceptionHandler;
import com.naicson.yugioh.util.exceptions.ErrorMessage;
import com.naicson.yugioh.util.search.CardSpecification;
import com.naicson.yugioh.util.search.SearchCriteria;

@Service
public class CardServiceImpl implements CardDetailService {
	
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private RelDeckCardsRepository relDeckCardsRepository;
	@Autowired
	EntityManager em;	
	@Autowired
	CardDAO dao;
	@Autowired
	CardOfUserDetailDTO cardUserDTO;
	@Autowired
	CardAlternativeNumberRepository alternativeRepository;
	
	Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);	
	
	public CardServiceImpl(CardRepository cardRepository, CardDAO dao, RelDeckCardsRepository relDeckCardsRepository, DeckRepository deckRepository) {
		this.cardRepository = cardRepository;
		this.dao = dao;
		this.relDeckCardsRepository = relDeckCardsRepository;
	}

	public CardServiceImpl() {
		
	}

	//Trazer o card para mostrar os detalhes;
	public Card cardDetails(Integer id) {
		Query query = em.createNativeQuery("SELECT * FROM TAB_CARDS WHERE ID = :deckId", Card.class);
		Card card = (Card) query.setParameter("deckId", id).getSingleResult();
		return card;
	}	
	
	@Override
	public List<Deck> cardDecks(Long cardNumero) {
		Query query = em.createNativeQuery("SELECT *  FROM tab_decks deck "
				+ "inner join TAB_REL_DECK_CARDS decks on deck.id = decks.deck_id " 
				+ " where decks.card_numero = :cardNumero ", Deck.class);
		
		List<Deck> decks_set = (List<Deck>) query.setParameter("cardNumero", cardNumero).getResultList();
		return decks_set;
	}
	
	@Override
	public List<RelUserCardsDTO> searchForCardsUserHave(int[] cardsNumbers) throws SQLException, ErrorMessage {
		try {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		
		if(user.getId() == 0) {
			throw new ErrorMessage("Unable to query user cards, user ID not entered");
		}
		
		if(cardsNumbers == null || cardsNumbers.length == 0) {
			throw new ErrorMessage("Unable to query user cards, decks IDs not entered");
		}
		
	     String cardsNumbersString = "";
	     
	     for(int id: cardsNumbers) {
	    	 cardsNumbersString += id;
	    	 cardsNumbersString += ",";
	     }	     
	     cardsNumbersString += "0";
	     
	     List<RelUserCardsDTO> relUserCardsList = dao.searchForCardsUserHave(user.getId(), cardsNumbersString);
		
	     return relUserCardsList;
	     
		}catch(ErrorMessage em) {
			throw em;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*@Override
	public CardAndSetsDTO findCardToAddToUserCollection (Long cardNumber) throws SQLException, ErrorMessage {
		
		Card card = cardRepository.findByNumero(cardNumber.longValue());
		
		if(card == null)
			throw new NoSuchElementException("It was not possible find a card to add to user's collection ");
		
		//Procura os decks e os set codes desse card
		List<RelDeckCards> rels = relDeckCardsRepository.findCardByNumberAndIsKonamiDeck(cardNumber);
		
		if(rels == null)
			throw new NoSuchElementException(" There is no deck associate with this card. ");
		
		Long[] arraySetsIds = new Long[rels.size()];
		//Coloca em um array pra poder buscar os decks com esse id
		for(int i = 0; i < rels.size(); i++) {
			arraySetsIds[i] = rels.get(i).getDeckId();
		}
		
		List<Deck> sets = deckRepository.findAllByIdIn(arraySetsIds);
		
			if(sets == null)
				throw new NoSuchElementException(" Zero deck was found.");
			
		Map <String, String> mapImgSetcode = new HashMap<>();
		
		for(RelDeckCards rel: rels ) {
			Deck setAux =  sets.stream().filter(set -> rel.getDeckId() == set.getId()).findAny().orElse(null);
			
			if(setAux != null)
			mapImgSetcode.put(rel.getCard_set_code(), setAux.getImagem());
		}
		
		UserDetailsImpl user = GeneralFunctions.userLogged();
		List<Deck> usersSets = deckRepository.findAllByUserId(user.getId());
		
		Map<Long, String> mapUsersSets = new HashMap<>();
		
		if(usersSets != null && usersSets.size() > 0) {
			for(Deck userSet: usersSets) {
				mapUsersSets.put(userSet.getId(), userSet.getNome());
			}
		}		
		
		CardAndSetsDTO dto = new CardAndSetsDTO();
		
		dto.setNumero(card.getNumero());
		dto.setNome(card.getNome());
		dto.setImagem(card.getImagem());
		dto.setMapDeckSetcode(mapImgSetcode);
		dto.setMapSetsOfUser(mapUsersSets);
		
		return dto;
	}*/
	
	@Override
	public List<Card> listar() {
		// TODO Auto-generated method stub
		return cardRepository.findAll();
	}

	@Override
	public Card listarId(int id) {
		// TODO Auto-generated method stub
		return cardRepository.findById(id);
	}

	@Override
	public Card add(Card card) {

		return cardRepository.save(card);
	}

	@Override
	public Card editar(Card card) {
		// TODO Auto-generated method stub
		return cardRepository.save(card);
	}

	@Override
	public Card deletar(int id) {
		// TODO Auto-generated method stub
		Card card = cardRepository.findById(id);
		if(card != null) {
			cardRepository.delete(card);
		}
		
		return card;
	}

	@Override
	public Card listarNumero(Long numero) {
		return cardRepository.findByNumero(numero);
	}

	@Override
	public List<CardOfArchetypeDTO> encontrarPorArchetype(Integer archId) {
		
		List<Card> cardsOfArchetype = cardRepository.findByArchetype(archId);
		
		if(cardsOfArchetype == null || cardsOfArchetype.isEmpty())
			throw new NoSuchElementException("It was not possible found cards of Archetype: " + archId);
		
		List<CardOfArchetypeDTO> listDTO = new ArrayList<>();
		
		  cardsOfArchetype.stream().forEach(card -> {
			CardOfArchetypeDTO dto = new CardOfArchetypeDTO(card);
			
			listDTO.add(dto);
		});
		
		return listDTO;
	}

	@Override
	public CardOfUserDetailDTO cardOfUserDetails(Long cardNumber) throws ErrorMessage, SQLException, Exception{
				
		try {

			UserDetailsImpl user = GeneralFunctions.userLogged();
						
			Card card = cardRepository.findByNumero(cardNumber);
			cardUserDTO = new CardOfUserDetailDTO();
			cardUserDTO.setCardImage(card.getImagem());
			cardUserDTO.setCardName(card.getNome());
			cardUserDTO.setCardNumber(card.getNumero());
			
			List<Tuple> cardsDetails = dao.listCardOfUserDetails(cardNumber, user.getId());
			
			if(cardsDetails != null) {
				//Mapeia o Tuple e preenche o objeto de acordo com as colunas da query
				List<CardsOfUserSetsDTO> listCardsSets = cardsDetails.stream().map(c -> new CardsOfUserSetsDTO(
						c.get(0, String.class),
						c.get(1, String.class),
						c.get(2, String.class),
						c.get(3, Double.class),
						c.get(4, BigInteger.class)
						)).collect(Collectors.toList());
				
				Map<String, Integer> mapRarity = new HashMap<>();
						
					listCardsSets.stream().forEach(r ->{
					//Verifica se ja tem essa raridade inserida no map
						if(!mapRarity.containsKey(r.getRarity())) {
							mapRarity.put(r.getRarity(), 1);
						} 
						else {
							//... se tiver acrescenta mais um 
							mapRarity.merge(r.getRarity(), 1, Integer::sum);
						}
				});
				
				cardUserDTO.setRarity(mapRarity);
				cardUserDTO.setSetsWithThisCard(listCardsSets);
	
			}
			
			return cardUserDTO;
			
		}catch (Exception ex) {
			throw ex;
		}
	}
	

	@Override
	public CardDetailsDTO findCardByNumberWithDecks(Long cardNumero) {
		
		Card card = cardRepository.findByNumero(cardNumero);
		
		if (card == null || card.getId() == null) {
			logger.error("It was not possible find card with number: {}".toUpperCase(), cardNumero);
			throw new EntityNotFoundException("It was not possible find card with number: " + cardNumero);
		}
		
		card = this.setAllCardSetsAndAlternativeNumbers(cardNumero, card);
		
		CardDetailsDTO dto = new CardDetailsDTO();
		
		dto.setCard(card);
		dto.setQtdUserHaveByKonamiCollection(this.findQtdUserHaveByCollection(card.getId(), "konami"));
		dto.setQtdUserHaveByUserCollection(this.findQtdUserHaveByCollection(card.getId(), "user"));
		return dto;
	}

	private Map<String, Integer> findQtdUserHaveByCollection(Integer cardId, String collectionSource) {
		UserDetailsImpl user = GeneralFunctions.userLogged();
		
		Map<String, Integer> mapCardSetAndQuantity = new HashMap<>();
		List<Tuple> total = null;
		
		if("konami".equalsIgnoreCase(collectionSource))
			total = cardRepository.findQtdUserHaveByKonamiCollection(cardId, user.getId());
		else if("user".equalsIgnoreCase(collectionSource))
			total = cardRepository.findQtdUserHaveByUserCollection(cardId, user.getId());
		else
			throw new IllegalArgumentException("Invalid collection source");
		
		if(total != null) {
			
			total.stream().forEach(relation -> {
				mapCardSetAndQuantity.put(relation.get(1, String.class), relation.get(0, BigInteger.class).intValue());
			});			
			
		} else {
			return Collections.emptyMap();
		}
		
		return mapCardSetAndQuantity;
	
	}

	
	private Card setAllCardSetsAndAlternativeNumbers(Long cardNumero, Card card) {
		
		card.setSets(this.cardDecks(cardNumero));
					
		if(card.getSets() != null && card.getSets().size() > 0) {
			
			card.getSets().stream().forEach(deck -> 
				deck.setRel_deck_cards(relDeckCardsRepository.findByDeckIdAndCardNumber(deck.getId(), cardNumero)));
		}
		
		card.setAlternativeCardNumber(alternativeRepository.findAllByCardId(card.getId()));
		
		return card;
	}
	
	@Override
	public List<CardsSearchDTO> getByGenericType(Pageable page, String genericType, long userId) {
		
		if(page == null || genericType == null || userId == 0)
			throw new IllegalArgumentException("Page, Generic Type or User Id is invalid.");
		
		Page<Card> list = cardRepository.getByGenericType(page, genericType, userId);
		
		if( list == null || list.isEmpty())
			throw new NoSuchElementException("No elements found with this parameters");
		
		List<CardsSearchDTO> dtoList = list.stream()
				.filter(card -> card != null)
				.map(card -> CardsSearchDTO.transformInDTO(card))
				.collect(Collectors.toList());
				
		return dtoList;		
		
	}

	@Override
	public Page<Card> findAll(CardSpecification spec, Pageable pageable) {
		if(spec == null )
			throw new IllegalArgumentException("No specification for card search");
		
		Page<Card> list = cardRepository.findAll(spec, pageable);
		
		return list;
	}
	
	@Override
	public List<CardsSearchDTO> cardSearch(List<SearchCriteria> criterias, String join, Pageable pageable) {
		
		CardSpecification spec = new CardSpecification();
		
		 criterias.stream().forEach(criterio -> 
			spec.add( new SearchCriteria(criterio.getKey(), criterio.getOperation(), criterio.getValue())));
		 			
		Page<Card> list = this.findAll(spec, pageable);
		
		List<CardsSearchDTO> dtoList = list.stream()
				.filter(card -> card != null)
				.map(card -> CardsSearchDTO.transformInDTO(card))
				.collect(Collectors.toList());
		
		if(!dtoList.isEmpty())
			dtoList.get(0).setTotalFound(list.getTotalElements());
		
		return dtoList;
			
	}
	
	@Override
	public Page<Card> searchCardDetailed(List<SearchCriteria> criterias, String join, Pageable pageable) {
		CardSpecification spec = new CardSpecification();
		
		criterias.stream().forEach(criterio ->
		spec.add(new SearchCriteria(criterio.getKey(), criterio.getOperation(), criterio.getValue())));
		
		Page<Card> list = this.findAll(spec, pageable);
		
		return list;
	}
	
	
	@Override
	public List<CardsSearchDTO> cardSearchByNameUserCollection(String cardName, Pageable pageable) {
		
		if(cardName == null || cardName.isEmpty())
			throw new IllegalArgumentException("Card name invalid for search");
		
		UserDetailsImpl user = GeneralFunctions.userLogged();
		
		Page<Card> cardsList = cardRepository.cardSearchByNameUserCollection(cardName, user.getId(), pageable);

		List<CardsSearchDTO> dtoList = new ArrayList<>();
		
		if(cardsList != null && !cardsList.isEmpty()) {
			
			dtoList = cardsList.stream()
					.filter(card -> card != null)
					.map(card -> CardsSearchDTO.transformInDTO(card))
					.collect(Collectors.toList());
		} else {
			dtoList = Collections.emptyList();
		}
		
		return dtoList;
	}

	@Override
	public List<Card> randomCardsDetailed()  {
			
		List<Card> cards = cardRepository.findRandomCards();
		
		if(cards == null || cards.isEmpty())
			 new ErrorMessage("Can't find random cards");		
	
		return cards;		
	}

	@Override
	public List<RelDeckCards> findAllRelDeckCardsByCardNumber(Long cardNumber) {
		if(cardNumber == null || cardNumber == 0)
			throw new IllegalArgumentException("Card number is invalid");
		
		List<RelDeckCards> list = this.relDeckCardsRepository.findByCardNumber(cardNumber);
		
		if(list == null)
			list = Collections.emptyList();
		
		return list;
		
	}

	@Override
	public CardAndSetsDTO findCardToAddToUserCollection(Long cardNumber) throws SQLException, ErrorMessage {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> findCardsNotRegistered(List<Long> cardsNumber) {
		
		if(cardsNumber == null || cardsNumber.isEmpty()) {
			logger.error("List with card numbers is invalid");
			throw new IllegalArgumentException("List with card numbers is invalid");
		}
		
		List<Long> cardsRegistered = cardRepository.findAllCardsByListOfCardNumbers(cardsNumber);
		List<Long> cardsNotRegistered = new ArrayList<>();
		
		if(cardsRegistered == null || cardsRegistered.isEmpty()) {
			cardsNotRegistered = cardsNumber;
			
		}else {
			cardsNotRegistered = this.verifyCardsNotRegistered(cardsNumber, cardsRegistered);
		}
		
		return cardsNotRegistered;
	}

	private List<Long> verifyCardsNotRegistered(List<Long> cardsNumber, List<Long> cardsRegistered) {
			
		if(cardsNumber == null || cardsNumber.isEmpty()) {
			logger.error("List with card numbers is invalid");
			throw new IllegalArgumentException("List with card numbers is invalid");
		}
		
		if(cardsRegistered == null || cardsRegistered.isEmpty()) {
			logger.error("There is no list of comparison since cards registered is empty");
			throw new IllegalArgumentException("There is no list of comparison since cards registered is empty");
		}
		
		if(cardsRegistered.containsAll(cardsNumber))
			return Collections.emptyList();
			
			cardsNumber.removeAll(cardsRegistered);
			
			return cardsNumber;		
	}

}
