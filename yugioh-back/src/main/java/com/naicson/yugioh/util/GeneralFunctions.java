package com.naicson.yugioh.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.naicson.yugioh.service.UserDetailsImpl;


public abstract class GeneralFunctions {
	
	static Logger logger = LoggerFactory.getLogger(GeneralFunctions.class);
	
	public static UserDetailsImpl userLogged() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		if(user == null || user.getId() == 0) {
			logger.error("COULD'T FIND THE LOGGED USER");
			throw new InternalAuthenticationServiceException("Could't find user");		
		}
						
		return user;
	}
	
	public static String transformArrayInString(int[] array) {
		
		String str = "";

		for (int ints : array) {
			str += ints;
			str += ",";
		}
		str += "0";
		
		return str;
	}
}
