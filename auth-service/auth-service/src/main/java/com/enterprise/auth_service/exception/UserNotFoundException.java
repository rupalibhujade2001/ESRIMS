package com.enterprise.auth_service.exception;

import org.springframework.http.HttpStatus;


public class UserNotFoundException extends Exception {
	
	HttpStatus statusCode;
	
	public UserNotFoundException(String message, HttpStatus statusCode){
		super(message);
		this.statusCode=statusCode;
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
		
	}
	

}
