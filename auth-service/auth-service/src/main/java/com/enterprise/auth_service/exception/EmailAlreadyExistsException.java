package com.enterprise.auth_service.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends RuntimeException{
	
	HttpStatus statusCode;
	
	public EmailAlreadyExistsException(String message,HttpStatus found)
	{
		super(message);
		this.statusCode=found;
		
	}
	
	public HttpStatus getStatus() {
		return statusCode;
		
		
	}

}
