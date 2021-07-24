package com.naicson.yugioh.util;

public class ErrorMessage extends Exception{
	
	private static final long serialVersionUID = 1149241039409861914L;
	
	public ErrorMessage(String msg) {
		super(msg);
	}
	
	public ErrorMessage(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
