package com.naicson.yugioh.configTest;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.naicson.yugioh.service.UserDetailsImpl;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {
	
	@Bean
	@Primary
	public InMemoryUserDetailsManager userService() {
		UserDetailsImpl basicUser = new UserDetailsImpl();
							
				basicUser.setPassword("123456");
				basicUser.setId(1);
				basicUser.setAuthorities(Arrays.asList(
		                new SimpleGrantedAuthority("ROLE_USER")));
				
				UserDetails user = basicUser;
				ArrayList<UserDetails> arr = new ArrayList<>();
				arr.add(User.withUsername("naicson88@hotmail.com").password("91628319").build());
				
		return new InMemoryUserDetailsManager(arr);
	}
}

