package com.naicson.yugioh;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YugiohApplicationTests {
	
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

}
