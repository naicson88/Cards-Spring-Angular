package com.naicson.yugioh.util.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
		
	ZonedDateTime time =  ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
			
		@ExceptionHandler(value = {Exception.class})
		public ResponseEntity<ApiExceptions> handleExceptionError(Exception e) {
			ApiExceptions ex = new ApiExceptions(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR, this.time);			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
		}
		
		@ExceptionHandler(value = {IllegalArgumentException.class})
		public ResponseEntity<Object> handleValiationException(IllegalArgumentException e){			
			ApiExceptions ex = new ApiExceptions(e.getMessage(), e, HttpStatus.BAD_REQUEST, this.time);	
			return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(value = {NoSuchElementException.class})
		public ResponseEntity<Object> handleNotFoundlErros(NoSuchElementException e){
			
			ApiExceptions ex = new ApiExceptions(e.getMessage(), e.getCause(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));	
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
		
		
}
