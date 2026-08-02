package com.crop.inventory_service.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InvalidPaginationRequestException extends RuntimeException {
	
	String message;
	HttpStatus HttpStatus;
	
	public  InvalidPaginationRequestException(String string, HttpStatus notFound) {
	
		this.message=string;
		this.HttpStatus=notFound;

	}

}
