package com.naicson.yugioh.service.interfaces;

import org.springframework.stereotype.Service;

import com.naicson.yugioh.dto.home.HomeDTO;

@Service
public interface HomeDetailService {
	
	HomeDTO getHomeDto();
}
