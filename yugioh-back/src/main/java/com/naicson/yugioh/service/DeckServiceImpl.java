package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naicson.yugioh.dao.DeckDAO;
import com.naicson.yugioh.dto.RelUserCardsDTO;
import com.naicson.yugioh.dto.RelUserDeckDTO;
import com.naicson.yugioh.dto.set.DeckDTO;
import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.RelDeckCards;
import com.naicson.yugioh.entity.sets.DeckUsers;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.repository.sets.DeckUsersRepository;
import com.naicson.yugioh.service.interfaces.DeckDetailService;
import com.naicson.yugioh.util.GeneralFunctions;
import com.naicson.yugioh.util.exceptions.ErrorMessage;

@Service
public class DeckServiceImpl implements DeckDetailService {


	@PersistenceContext
	EntityManager em;
	
	@Autowired
	DeckDAO dao;
	
	@Autowired
	DeckRepository deckRepository;
	
	@Autowired 
	DeckUsersRepository deckUserRepository;
	
	@Autowired
	RelDeckCardsRepository relDeckCardsRepository;
	
	Logger logger = LoggerFactory.getLogger(DeckServiceImpl.class);
	
	public DeckServiceImpl(DeckRepository deckRepository, RelDeckCardsRepository relDeckCardsRepository, DeckDAO dao, DeckUsersRepository deckUserRepo) {
		this.deckRepository = deckRepository;
		this.relDeckCardsRepository = relDeckCardsRepository;
		this.dao = dao;
		this.deckUserRepository = deckUserRepo;
	}

	public DeckServiceImpl() {
	
	}

	@Override
	public Deck findById(Long Id) {		
			if(Id == null || Id == 0)
				throw new IllegalArgumentException("Deck Id informed is invalid.");
					
			 Deck deck =  deckRepository.findById(Id).orElseThrow(() -> new NoSuchElementException("Deck not found."));	
			 
			 return deck;
	}

	//Traz informações da relação entre o deck e os cards
	@Override
	public List<RelDeckCards> relDeckCards(Long deckId) {

		if(deckId == null || deckId == 0)
			throw new IllegalArgumentException("Deck Id informed is invalid.");
		
		List<RelDeckCards> relation = relDeckCardsRepository.findByDeckId(deckId);
		
		return relation;
		
	}
	
	@Override
	public List<RelDeckCards> relDeckUserCards(Long deckUserId){
		if(deckUserId == null || deckUserId == 0)
			throw new IllegalArgumentException("Deck User Id informed is invalid");
		
		List<RelDeckCards> relation = dao.relDeckUserCards(deckUserId);
		
		if(relation.isEmpty())
			throw new NoSuchElementException("Can't find relation");
		
		return relation;
	}


	@Override
	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int addSetToUserCollection(Long originalDeckId) throws SQLException, ErrorMessage, Exception {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String customizeDeckName = GeneralFunctions.momentAsString();

			// Consulta o deck pelo Id
			Deck deckOrigem = deckRepository.findById(originalDeckId).orElseThrow(() -> new ErrorMessage("No Set found with this code."));			
			

			if (user == null) {
				logger.error("It was not able found user!".toUpperCase());
				throw new ErrorMessage("It was not able found user!");
			}
				
				// Cria um novo deck que será inserido como do usuário
				
				DeckUsers newDeck = new DeckUsers();
				newDeck.setImagem(deckOrigem.getImagem());
				newDeck.setNome(deckOrigem.getNome()+"_"+customizeDeckName);
				newDeck.setKonamiDeckCopied(deckOrigem.getId());
				
				newDeck.setUserId(user.getId());
				newDeck.setDtCriacao(new Date());
				newDeck.setSetType(deckOrigem.getSetType());
							
				
				if (newDeck.getNome() == null  || newDeck.getUserId() == 0) {
					logger.error("It was not possible find de Deck!".toUpperCase());
					throw new ErrorMessage("It was not possible find de Deck!");
				}
				
				DeckUsers generatedDeckId = deckUserRepository.save(newDeck);

				if (generatedDeckId == null) {
					logger.error("It was not possible add Deck to user.".toUpperCase());
					throw new ErrorMessage("It was not possible add Deck to user.");
				}
				
				//Adiciona os cards do Deck original ao novo Deck.
				int addCardsOnNewDeck = this.addCardsToUserDeck(originalDeckId, generatedDeckId.getId());
				
				if(addCardsOnNewDeck <= 0) {
					logger.error("It was not possible add cards to the new Deck.".toUpperCase());
					throw new ErrorMessage("It was not possible add cards to the new Deck.");
				}
				
				//Adiciona os cards a coleção do usuário.
				 int addCardsToUsersCollection = this.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(), "A");

				if (addCardsToUsersCollection < 1) {
					throw new ErrorMessage("Unable to include Cards for User!");
				}

				return addCardsToUsersCollection;
	
		} catch (ErrorMessage msg) {
			throw msg;
		} 
	}
	

	@Override
	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int ImanegerCardsToUserCollection(Long originalDeckId, String flagAddOrRemove)
			throws SQLException, ErrorMessage {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			int itemAtualizado;

			Integer alreadyHasThisDeck = dao.verifyIfUserAleadyHasTheDeck(originalDeckId, user.getId());

			// Se o usuário não tiver o Deck e for passado parametro para remover esse deck.
			if (alreadyHasThisDeck != null && alreadyHasThisDeck == 0 && flagAddOrRemove.equals("R")) {
				return 0;
			}

			if (alreadyHasThisDeck != null && alreadyHasThisDeck == 0) {
				itemAtualizado = dao.addDeckToUserCollection(originalDeckId, user.getId());

				if (itemAtualizado < 1) {
					throw new ErrorMessage("Unable to include Deck for User!");
				}

			} else {
				itemAtualizado = dao.changeQuantitySpecificDeckUserHas(originalDeckId, user.getId(), flagAddOrRemove);

				if (itemAtualizado < 1) {
					throw new ErrorMessage("Unable to manege Deck for User!");
				}
			}

			int qtdAddedOrRemoved = this.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(),
					flagAddOrRemove);

			if (qtdAddedOrRemoved < 1) {
				throw new ErrorMessage("Unable to include Cards for User!");
			}

			return qtdAddedOrRemoved;

		} catch (ErrorMessage msg) {
			throw msg;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int addOrRemoveCardsToUserCollection(Long originalDeckId, long userId, String flagAddOrRemove)
			throws SQLException, ErrorMessage {
		try {
			int qtdCardsAddedOrRemoved = 0;

			List<DeckDTO> relDeckAndCards = dao.relationDeckAndCards(originalDeckId);

			if (relDeckAndCards != null && relDeckAndCards.size() > 0) {
				if (flagAddOrRemove.equals("A") || flagAddOrRemove.equals("R")) {

					for (DeckDTO relation : relDeckAndCards) {
						// Verifica se o usuário ja possui essa carta.
						boolean alreadyHasThisCard = dao.verifyIfUserAleadyHasTheCard(userId,
								relation.getCard_set_code());

						if (alreadyHasThisCard == true) {
							// Remove ou adiciona a qtd desta carta de acordo com a flag passada.
							int manegeQtd = dao.changeQuantityOfEspecifCardUserHas(userId, relation.getCard_set_code(),
									flagAddOrRemove);

							if (manegeQtd < 1) {
								throw new ErrorMessage("It was not possible to manege card to the user's collection!");
							}

							qtdCardsAddedOrRemoved++;

						} else {
							// Caso o usuário não tenha o Card, simplesmente da um insert desse card na
							// coleção do usuário.
							RelUserCardsDTO rel = new RelUserCardsDTO();
							rel.setUserId(userId);
							rel.setCardNumero(relation.getCard_numero());
							rel.setCardSetCode(relation.getCard_set_code());
							rel.setQtd(1);
							rel.setDtCriacao(new Date());
							
							int insertCard = dao.insertCardToUserCollection(rel);

							if (insertCard < 1) {
								throw new ErrorMessage(
										"It was not possible to add this Card to the user's collection.");
							}

							qtdCardsAddedOrRemoved++;
						}
					}

				} else {
					throw new ErrorMessage("Check the Add or Remove parameter sent!");
				}

			} 

			return qtdCardsAddedOrRemoved;

		} catch (ErrorMessage msg) {
			throw msg;
		}
	}
	

	@Override
	public List<RelUserDeckDTO> searchForDecksUserHave(Long[] decksIds) throws SQLException, ErrorMessage {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

			if (user.getId() == 0) {
				throw new ErrorMessage("Unable to query user decks, user ID not entered");
			}

			if (decksIds == null || decksIds.length == 0) {
				throw new ErrorMessage("Unable to query user decks, decks IDs not entered");
			}

			String decksIdsString = "";

			for (Long id : decksIds) {
				decksIdsString += id;
				decksIdsString += ",";
			}
			decksIdsString += "0";

			List<RelUserDeckDTO> relUserDeckList = dao.searchForDecksUserHave(user.getId(), decksIdsString);

			return relUserDeckList;

		} catch (ErrorMessage em) {
			throw em;
		} catch (Exception e) {
			throw e;
		}
	}
	

	@Override
	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public Long addDeck(Deck deck) throws SQLException, ErrorMessage {
		Long id = 0L;

		if (deck != null) {
			dao = new DeckDAO();
			id = dao.addDeck(deck);
		} else {
			throw new ErrorMessage("O deck informado não é válido.");
		}

		return id;
	}
	
	@Override
	//@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int addCardsToUserDeck(Long originalDeckId, Long generatedDeckId) throws SQLException, Exception, ErrorMessage {
		try {	
			
			if (originalDeckId == null && generatedDeckId == null) {
				throw new ErrorMessage("Original deck or generated deck is null");
			}
			
			int cardsAddedToDeck = dao.addCardsToDeck(originalDeckId, generatedDeckId);
			
			return cardsAddedToDeck;
			
		}catch(ErrorMessage em) {
			throw em;
		}
	}
	

	@Override
	public int removeSetFromUsersCollection(Long setId) throws SQLException, ErrorMessage, Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
	
		// Consulta o deck pelo Id
		Optional<DeckUsers> dk = deckUserRepository.findById(setId);
		
		if(dk.isEmpty()) {
			logger.error("Set not found with this code. Id = ".toUpperCase() + setId);
			throw new ErrorMessage("Set not found with this code. Id = " + setId);
		}
		
		DeckUsers setOrigem = dk.get();
		
		//Remove os cards da coleção do usuário. 
		//int qtdRemoved = this.addOrRemoveCardsToUserCollection(setId, user.getId(), "R");
		
		//Remove os Cards do Set.
		int qtdRemoved = dao.removeCardsFromUserSet(setId);
		
		//Remove o Set.
		deckUserRepository.deleteById(setOrigem.getId());
		
		return qtdRemoved;
		
	}

	@Override
	public List<Deck> findByNomeContaining(String nomeDeck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Card> cardsOfDeck(Long deckId) {
		
		List<Card> cards = dao.cardsOfDeck(deckId);
		
		if(cards == null || cards.size() == 0)
			throw new IllegalArgumentException("Can't find cards of this Set.");
		
		return cards;
	}
	

	@Override
	public Deck deckAndCards(Long deckId, String setType) throws Exception {
		
		Deck deck = new Deck();
		
		if(("Konami").equalsIgnoreCase(setType)) {
			
			deck =  this.returnKonamiDeck(deckId);
		}
		else if(("User").equalsIgnoreCase(setType)) {
			
			deck = this.returnUserDeck(deckId);
			
		} else {
			logger.error("INVALID SETTYPE. SETTYPE WAS = " + setType);
			throw new IllegalArgumentException("SetType invalid. SetType was = " + setType);			
		}			
		
		return deck;
	}
	
	private Deck returnKonamiDeck(Long deckId) {
		
		if(deckId == null || ("").equals(deckId))
			throw new  IllegalArgumentException("Invalid Deck Id. deckId = " + deckId);
		
		Deck deck = new Deck();
		List<Card> mainDeck = null;
		deck = this.findById(deckId);
		
		mainDeck = this.cardsOfDeck(deckId);
		List<RelDeckCards> relDeckCards = this.relDeckCards(deckId);
		
		deck.setCards(mainDeck);
		deck.setRel_deck_cards(relDeckCards);
		
		return deck;
		
	}
	
	private Deck returnUserDeck(Long deckId) throws ErrorMessage {
		
		if(deckId == null || ("").equals(deckId))
			throw new  IllegalArgumentException("Invalid Deck Id. deckId = " + deckId);
		
		Deck deck = new Deck();
		
		List<Card> mainDeck = null;
		DeckUsers deckUser = new DeckUsers();
		
		deckUser = this.deckUserRepository.findById(deckId).orElseThrow(() -> new EntityNotFoundException("UserDeck id = " + deckId));
		
		deck.setNome(deckUser.getNome());
		deck.setImagem(deckUser.getImagem());
		deck.setDt_criacao(deckUser.getDtCriacao());
		deck.setId(deckUser.getId());
		
		mainDeck = this.consultMainDeck(deckId);
		
		List<Card> sideDeckCards = dao.consultSideDeckCards(deckId, "User");
		List<Card> extraDeck = this.consultExtraDeckCards(deckId, "User");
		
		deck.setCards(mainDeck);
		deck.setExtraDeck(extraDeck);
		deck.setSideDeckCards(sideDeckCards);
		
		deck.setRel_deck_cards(this.relDeckUserCards(deckId));
		
		int sumDecks = deck.getCards().size() + deck.getExtraDeck().size() + deck.getSideDeckCards().size();
		
		if(sumDecks != deck.getRel_deck_cards().size())
			throw new ErrorMessage("Cards quantity don't match relation quantity." + 
					"Param received: deckId = " + deckId + " setType = User");
		
		return deck;
	}
	
	@Override
	public List<Card> consultMainDeck(Long deckId) {
		if(deckId == null || deckId == 0)
			throw new IllegalArgumentException("Invalid Deck ID");
		
		List<Card> mainDeck = dao.consultMainDeck(deckId);
		
		if(mainDeck == null || mainDeck.isEmpty())
			throw new NoSuchElementException("No cards found for Main Deck");
	
		mainDeck = this.sortMainDeckCards(mainDeck);
		
		return mainDeck;
		
	}
	
	@Override
	public List<Card> consultExtraDeckCards(Long deckId, String userOrKonamiDeck) {
		if(deckId == null || deckId == 0)
			throw new IllegalArgumentException("Invalid Deck ID");
		
		List<Card> extraDeckCards = dao.consultExtraDeckCards(deckId, userOrKonamiDeck);
		
		return extraDeckCards;
	}
	
	@Override
	public List<Card> sortMainDeckCards(List<Card> cardList) {
		
		if(cardList != null && cardList.isEmpty())
			throw new IllegalArgumentException("Card List is empty");
		
		List<Card> sortedCardList = new ArrayList<>();
		
		//Insere primeiro os cards do tipo Monstro
		cardList.stream().filter(card -> card.getNivel() != null) //.sorted(Comparator.comparing(Card::getNome))
		.collect(Collectors.toCollection(() -> sortedCardList));
			
		//Coloca o restante das cartas
		cardList.stream().filter(card -> card.getNivel() == null)
		.sorted((c1, c2) -> c1.getGeneric_type().compareTo(c2.getGeneric_type()))
		.collect(Collectors.toCollection(() -> sortedCardList));
				
		return sortedCardList;
	}
	
  @Transactional
  @Override
  public void saveUserdeck(Deck deck) throws SQLException {
	DeckUsers userDeck = new DeckUsers();
	
	if(deck.getNome() == null || deck.getNome().equals("")) {
		logger.error("USERDECK NAME IS NULL OR EMPTY");
		throw new IllegalArgumentException("UserDeck name cannot be null or empty");		
	}
	
	if( deck.getRel_deck_cards() == null ||  deck.getRel_deck_cards().isEmpty()) {
		logger.error("DECK HAVE NO CARD INSERTED");
		throw new IllegalArgumentException("There is no card in this deck");	
	}
	
	//Check if it is a new deck or a existing deck
	if(deck.getId() != null && deck.getId() != 0) {
		dao.deleteCardsDeckuserByDeckId(deck.getId());
		userDeck = deckUserRepository.getOne(deck.getId());
		
	} else {
		UserDetailsImpl user = GeneralFunctions.userLogged();
		userDeck.setUserId(user.getId());
		userDeck.setDtCriacao(new Date());
		userDeck.setSetType("D");
	}		
	
		//FUTURAMENTE COLOCAR PARA EDITAR IMAGEM DO DECK
		userDeck.setNome(deck.getNome());
		userDeck = deckUserRepository.save(userDeck);
		
		if(userDeck == null) {
			logger.error("IT WAS NOT POSSIBLE INSERT A NEW USERDECK");
			throw new SQLException("It was not possible create/update the Deck");
		}	
		
	for(RelDeckCards rel : deck.getRel_deck_cards()) {
		
		if(rel.getCard_price() == null )
			rel.setCard_price(0.00);
		
		int isSaved = dao.saveRelDeckUserCard(rel, userDeck.getId());
		
		if(isSaved == 0) {
			logger.error("IT WAS NOT POSSIBLE SAVE A CARD IN USERDECK");
			throw new SQLException("It was not possible save the card " + rel.getCard_set_code());
		}		
	}
		
 }
  
}
