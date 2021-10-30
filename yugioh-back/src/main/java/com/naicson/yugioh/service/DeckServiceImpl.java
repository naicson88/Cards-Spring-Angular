package com.naicson.yugioh.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import com.naicson.yugioh.entity.sets.SetType;
import com.naicson.yugioh.repository.DeckRepository;
import com.naicson.yugioh.repository.RelDeckCardsRepository;
import com.naicson.yugioh.repository.sets.DeckUsersRepository;
import com.naicson.yugioh.util.ErrorMessage;

@Service
public class DeckServiceImpl implements DeckDetailService {


	@PersistenceContext
	EntityManager em;
	
	@Autowired
	DeckDAO dao;
	
	DeckRepository deckRepository;
	
	@Autowired 
	DeckUsersRepository deckUserRepository;
	
	@Autowired
	RelDeckCardsRepository relDeckCardsRepository;
	
	public DeckServiceImpl(DeckRepository deckRepository, RelDeckCardsRepository relDeckCardsRepository, DeckDAO dao, DeckUsersRepository deckUserRepo) {
		this.deckRepository = deckRepository;
		this.relDeckCardsRepository = relDeckCardsRepository;
		this.dao = dao;
		this.deckUserRepository = deckUserRepo;
	}

	public DeckServiceImpl() {
	
	}

	@Override
	public Optional<Deck> findById(Long Id) {		
			if(Id == null || Id == 0)
				throw new IllegalArgumentException("Deck Id informed is invalid.");
			
			return deckRepository.findById(Id);					
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
	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
	public int addSetToUserCollection(Long originalDeckId) throws SQLException, ErrorMessage, Exception {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

			// Consulta o deck pelo Id
			Deck deckOrigem = deckRepository.findById(originalDeckId).orElseThrow(() -> new ErrorMessage("No Set found with this code."));			
			

			if (user != null) {
				
				// Cria um novo deck que será inserido como do usuário
				
				DeckUsers newDeck = new DeckUsers();
				newDeck.setImagem(deckOrigem.getImagem());
				newDeck.setNome(deckOrigem.getNome());
				newDeck.setKonamiDeckCopied(deckOrigem.getId());
				
				newDeck.setUserId(user.getId());
				newDeck.setDtCriacao(new Date());
				newDeck.setSetType(deckOrigem.getSetType());
							
				
				if (newDeck != null) {
					
					DeckUsers generatedDeckId = deckUserRepository.save(newDeck);

					if (generatedDeckId == null) {
						throw new ErrorMessage("It was not possible add Deck to user.");
					}
					
					//Adiciona os cards do Deck original ao novo Deck.
					int addCardsOnNewDeck = this.addCardsToUserDeck(originalDeckId, generatedDeckId.getId());
					
					if(addCardsOnNewDeck <= 0) {
						throw new ErrorMessage("It was not possible add cards to the new Deck.");
					}
					
					//Adiciona os cards a coleção do usuário.
					 int addCardsToUsersCollection = this.addOrRemoveCardsToUserCollection(originalDeckId, user.getId(), "A");

					if (addCardsToUsersCollection < 1) {
						throw new ErrorMessage("Unable to include Cards for User!");
					}

					return addCardsToUsersCollection;

				} else {
					throw new ErrorMessage("It was not possible find de Deck!");
				}
			} else {
				throw new ErrorMessage("It was not able found user!");
			}

		} catch (ErrorMessage msg) {
			throw msg;
		} catch (Exception e) {
			throw e;
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
	public int addOrRemoveCardsToUserCollection(Long originalDeckId, int userId, String flagAddOrRemove)
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
		} catch (Exception ex) {
			throw ex;
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
	@Transactional(rollbackFor = {Exception.class, ErrorMessage.class, SQLException.class})
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
		Optional<Deck> dk = deckRepository.findById(setId);
		
		if(dk.isEmpty()) {
			throw new ErrorMessage("No Set found with this code.");
		}
		
		Deck setOrigem = dk.get();
		//Não pode remover um Set que é da Konami.
		if(setOrigem.getIsKonamiDeck().equals("S")) {
			throw new ErrorMessage("Konami Set cannot be deleted!");
		}
		
		//Remove os cards da coleção do usuário. 
		int qtdRemoved = this.addOrRemoveCardsToUserCollection(setId, user.getId(), "R");
		
		//Remove os Cards do Set.
		dao.removeCardsFromSet(setId);
		
		//Remove o Set.
		deckRepository.deleteById(setOrigem.getId());
		
		return qtdRemoved;
		
	}

	@Override
	public List<Deck> findByNomeContaining(String nomeDeck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Card> cardsOfDeck(Long deckId) {
		// TODO Auto-generated method stub
		return null;
	}



}
