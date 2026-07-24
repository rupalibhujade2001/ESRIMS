package com.crop.product_service.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crop.product_service.service.impl.ProductServiceImpl;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ProductFoundException.class)
	public ResponseEntity<String> handleInventoryServicedown(RuntimeException e)
	{
		return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> ProductNotFoundException(ResourceNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		
	}
	
	
	

}
