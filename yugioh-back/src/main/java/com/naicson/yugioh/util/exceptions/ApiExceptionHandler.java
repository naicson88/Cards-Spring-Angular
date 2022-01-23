package com.naicson.yugioh.util.exceptions;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ApiExceptionHandler {
		
	ZonedDateTime time =  ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
	
	Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
			
		@ExceptionHandler(value = {Exception.class})
		public ResponseEntity<ApiExceptions> handleExceptionError(Exception e) {
			ApiExceptions ex = new ApiExceptions(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, this.time);			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
		}
		
		@ExceptionHandler(value = {IllegalArgumentException.class})
		public ResponseEntity<Object> handleValiationException(IllegalArgumentException e){			
			ApiExceptions ex = new ApiExceptions(e.getMessage(), e.getCause(), HttpStatus.BAD_REQUEST, this.time);	
			return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(value = {NoSuchElementException.class})
		public ResponseEntity<Object> handleNotFoundlErros(NoSuchElementException e){	
			ApiExceptions ex = new ApiExceptions(e.getMessage(), e.getCause(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));	
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
		
		@ExceptionHandler(value = {EntityNotFoundException.class})
		public ResponseEntity<Object> handleEntityNotFoundlErros(EntityNotFoundException e){			
			ApiExceptions ex = new ApiExceptions(e.getMessage(), e.getCause(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));	
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
			
		@ExceptionHandler(value = {SQLException.class})
			public ResponseEntity<Object> handleSQLException(SQLException sql){
				ApiExceptions ex = new ApiExceptions(sql.getMessage(), sql, HttpStatus.INTERNAL_SERVER_ERROR, this.time);
				logger.error("SQLException: " + ex.getMsg() + ", " + ex.getCause());
				
				return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		@ExceptionHandler(value = {ErrorMessage.class})
		public ResponseEntity<Object> handleErrorMessage(ErrorMessage em){
			ApiExceptions ex = new ApiExceptions(em.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, this.time);
			logger.error("ErrorMessage: " + em.getMessage());
			
			return new ResponseEntity<Object>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		}
		
	

