package com.naicson.yugioh.util.exceptions;

public class ApiRequestExceptions extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ApiRequestExceptions(String msg) {
		super(msg);
	}
	
	public ApiRequestExceptions(String msg, Throwable cause) {
		super(msg, cause);
	}
}
