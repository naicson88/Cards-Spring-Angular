package com.naicson.yugioh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.home.HomeDTO;
import com.naicson.yugioh.repository.HomeRepository;
import com.naicson.yugioh.service.interfaces.HomeDetailService;
import com.naicson.yugioh.util.GeneralFunctions;

@Service
public class HomeServiceImpl implements HomeDetailService{
	
	@Autowired
	HomeRepository homeRepository;

	@Override
	public HomeDTO getHomeDto() {
		HomeDTO homeDto = new HomeDTO();
		UserDetailsImpl user = GeneralFunctions.userLogged();
		
		homeDto.setQtdDeck(homeRepository.returnQuantitySetType("D", user.getId()));
		homeDto.setQtdBoxes(homeRepository.returnQuantitySetType("B", user.getId()));
		homeDto.setQtdTins(homeRepository.returnQuantitySetType("T", user.getId()));
		homeDto.setQtdCards(homeRepository.returnQuantityCardsUserHave(user.getId()));		
		
		return homeDto;
	}
	
	
}
