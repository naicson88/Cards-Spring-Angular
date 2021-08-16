package com.naicson.yugioh.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.naicson.yugioh.service.UserDetailsImpl;


public abstract class GeneralFunctions {
	
	public static UserDetailsImpl userLogged() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		return user;
	}
}
