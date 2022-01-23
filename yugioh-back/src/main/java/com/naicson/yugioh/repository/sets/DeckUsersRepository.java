package com.naicson.yugioh.repository.sets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.Deck;
import com.naicson.yugioh.entity.sets.DeckUsers;

@Repository
public interface DeckUsersRepository extends JpaRepository<DeckUsers, Long>{
	
	Page<DeckUsers> findAllByUserIdAndSetType(long userId, String setType, Pageable pageable);

}
