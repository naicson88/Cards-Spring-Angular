package com.naicson.yugioh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import Util.AdicionarCardsNoSet;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class YugiohApplication {

	public static void main(String[] args) {
		SpringApplication.run(YugiohApplication.class, args);
		
		AdicionarCardsNoSet add = new AdicionarCardsNoSet();
		
		add.InserirNosSets();
	}

}
