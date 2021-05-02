package com.naicson.yugioh;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naicson.yugioh.controller.AuthController;
import com.naicson.yugioh.entity.SignupRequest;
import com.naicson.yugioh.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(controllers = AuthController.class)
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class ControllerTest  {
	
	@Autowired
	private MockMvc mvc;
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	AuthController authController;
	@Autowired
	 private ObjectMapper objectMapper;
	
	/*@Test
	void whenValid_thenReturns200() throws Exception {
		final Set<String> roles = new HashSet<>();
		roles.add("admin");
		SignupRequest sign = new SignupRequest("alan", "email@teste.com.br", "admin", "91628319");
		
		 mvc.perform(post("/forums/{forumId}/register", 42L)
			        .contentType("application/json")			     
			        .content(objectMapper.writeValueAsString(sign)))
			        .andExpect(status().isOk());
	}*/
	
}
