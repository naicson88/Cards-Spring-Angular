package com.naicson.yugioh.util.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ApiExceptions {
	
	private final String msg;
	private final Throwable cause;
	private final HttpStatus httpStatus;
	private final ZonedDateTime time;
	
	public ApiExceptions(String msg, Throwable cause, HttpStatus httpStatus, ZonedDateTime time) {
		super();
		this.msg = msg;
		this.cause = cause;
		this.httpStatus = httpStatus;
		this.time = time;
	}
	
	public ApiExceptions(String msg, HttpStatus httpStatus, ZonedDateTime time) {
		this.msg = msg;
		this.cause = new Throwable();		
		this.httpStatus = httpStatus;
		this.time = time;	
	}
	
	public String getMsg() {
		return msg;
	}
	public Throwable getCause() {
		return cause;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public ZonedDateTime getTime() {
		return time;
	}
	
}
