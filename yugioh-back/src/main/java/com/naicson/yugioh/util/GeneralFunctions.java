package com.naicson.yugioh.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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
	
    public static String momentAsString() {
    	String hour = String.valueOf(LocalDateTime.now().getHour());
    	String minutes = String.valueOf(LocalDateTime.now().getMinute());
    	String seconds = String.valueOf(LocalDateTime.now().getSecond());
    	
    	String day = String.valueOf(LocalDateTime.now().getDayOfMonth());
    	String month = String.valueOf(LocalDateTime.now().getMonthValue());
    	String year = String.valueOf(LocalDateTime.now().getYear());
    	
    	String moment = day+month+year+hour+minutes+seconds;
    	
    	return moment;
    }
    
    public static void saveCardInFolder(Long cardNumber) {
    	try(InputStream in = new URL("https://storage.googleapis.com/ygoprodeck.com/pics/"+cardNumber+".jpg").openStream()) {
    		Files.copy(in, Paths.get("C:\\Cards"+cardNumber+".jpg"));
    		
    	}catch(IOException e) {
    		e.getMessage();
    	}
    }
}
