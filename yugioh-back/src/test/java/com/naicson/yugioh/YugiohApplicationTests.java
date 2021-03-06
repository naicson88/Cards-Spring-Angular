package com.naicson.yugioh;



import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naicson.yugioh.controller.AuthController;
import com.naicson.yugioh.entity.SignupRequest;
import com.naicson.yugioh.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class YugiohApplicationTests {
	
	@Autowired
	private MockMvc mvc;
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	AuthController authController;
	@Autowired
	 private ObjectMapper objectMapper;
	
	Calculator teste = new Calculator();
	
	@Test
	void contextLoads() {
		//given
		int numberOne = 20;
		int numberTwo = 15;
		
		//when
		int result = teste.add(numberOne, numberTwo);
		
		//then
		assertThat(result).isEqualTo(35);
	}
	
	class Calculator{
		int add(int a, int b) {
			return a + b;
		}
	}
	
	/*@Test
	void whenValid_thenReturns200() throws Exception {
		final Set<String> roles = new HashSet<>();
		roles.add("admin");
		SignupRequest sign = new SignupRequest("alan", "email@teste.com.br", roles, "91628319");
		
		 mvc.perform(post("yugiohAPI/auth/signup", 42L)
			        .contentType("application/json")			     
			        .content(objectMapper.writeValueAsString(sign)))
			        .andExpect(status().isOk());
	}*/
}
