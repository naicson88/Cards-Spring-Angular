 package com.naicson.yugioh.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Card;
import com.naicson.yugioh.util.search.CardSpecification;


@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
	
	List<Card> findAll();
	
	Page<Card> findAll(Pageable pageable);
	
	Card findById(int id);
	
	Card save (Card card);
	
	Card findByNumero(Long numero);
	
	void delete (Card card);
	
	Card findByNumero(String numero);
	
	@Query(value = "SELECT * FROM tab_cards WHERE COD_ARCHETYPE = :archId",  nativeQuery = true)	
	List<Card> findByArchetype(Integer archId);
	
	@Query(value = "SELECT * FROM tab_cards ORDER BY RAND() LIMIT 30",  nativeQuery = true)
	List<Card> findRandomCards();
	
	@Query(value = "SELECT * FROM tab_cards cards "
			+ " inner join tab_rel_deck_cards rcards on rcards.card_numero = cards.numero "
			+ " inner join tab_decks deck on deck.id = rcards.deck_id "
			+ " where deck.user_id = :userId and cards.generic_type = :type ",
			nativeQuery = true)
	Page<Card> findCardsByTypeAndUser(String type, int userId, Pageable page);
	
	@Query(value = " SELECT DISTINCT * FROM yugioh.tab_cards CARDS "
			+ " INNER JOIN TAB_REL_DECKUSERS_CARDS UCARDS ON UCARDS.CARD_NUMERO = CARDS.NUMERO "
			+ " INNER JOIN TAB_DECK_USERS DUSERS ON DUSERS.ID = UCARDS.DECK_ID "
			+ " WHERE CARDS.GENERIC_TYPE = :genericType AND DUSERS.USER_ID = :userId "
			+ " GROUP BY CARDS.NUMERO ",
			countQuery = "SELECT count(*) FROM tab_cards",
			nativeQuery=true)
	Page<Card> getByGenericType (Pageable page, String genericType, long userId);
	
	@Query(value = " SELECT DISTINCT * FROM yugioh.tab_cards CARDS "
			+ " INNER JOIN TAB_REL_DECKUSERS_CARDS UCARDS ON UCARDS.CARD_NUMERO = CARDS.NUMERO "
			+ " INNER JOIN TAB_DECK_USERS DUSERS ON DUSERS.ID = UCARDS.DECK_ID "
			+ " WHERE CARDS.nome like CONCAT('%',:cardName,'%') AND DUSERS.USER_ID = :userId "
			+ " GROUP BY CARDS.NUMERO ",
			countQuery = "SELECT count(*) FROM tab_cards",
			nativeQuery=true)
	Page<Card> cardSearchByNameUserCollection(String cardName, long userId, Pageable pageable);
	
	@Query(value = "select numero from tab_cards where numero in :cardsNumber", nativeQuery = true)
	List<Long> findAllCardsByListOfCardNumbers(List<Long> cardsNumber);

	Card findByNome(String nome);
	
	@Query(value = " SELECT count(rel.card_set_code) as total, CONCAT(decks.nome, ' (', rel.card_set_code,')' ) AS card_set "
	+ " FROM yugioh.tab_rel_deckusers_cards as rel\r\n"
	+ " inner join tab_rel_deck_cards rdc on rdc.card_set_code = rel.card_set_code"
	+ " inner join tab_decks decks on decks.id = rdc.deck_id\r\n"
	+ " inner join tab_deck_users du on du.id = rel.deck_id\r\n"
	+ " where du.user_id = :userId "
	+ " and  rel.card_numero in (select card_alternative_number from tab_card_alternative_numbers where card_id = :cardId) "
	+ " group by rel.card_set_code, decks.nome", nativeQuery = true)
	List<Tuple> findQtdUserHaveByKonamiCollection(Integer cardId, long userId);
	
	@Query(value = " SELECT count(rel.card_set_code) as total, CONCAT(du.nome, ' (', rel.card_set_code,')' ) AS card_set "
	+ " FROM yugioh.tab_rel_deckusers_cards as rel\r\n"
	+ " inner join tab_rel_deck_cards rdc on rdc.card_set_code = rel.card_set_code"
	+ " inner join tab_deck_users du on du.id = rel.deck_id\r\n"
	+ " where du.user_id = :userId "
	+ " and  rel.card_numero in (select card_alternative_number from tab_card_alternative_numbers where card_id = :cardId) "
	+ " group by rel.card_set_code, du.nome", nativeQuery = true)
	List<Tuple> findQtdUserHaveByUserCollection(Integer cardId, long userId);
	
	
}
