package com.crop.product_service.service.exception;

import org.springframework.http.HttpStatus;


public class ProductFoundException extends RuntimeException{
	
	HttpStatus status;
	
	public ProductFoundException(String message, HttpStatus statusCode){
		super(message);
		this.status=statusCode;
	}
	

}
