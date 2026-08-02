package com.crop.inventory_service.service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InventoryException.class)
	public ResponseEntity<ErrorResponse> invalidQuantity(InventoryException e){
		return ResponseEntity.badRequest().body( new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.toString(),e.getMessage()));
	}
	@ExceptionHandler(InvalidPaginationRequestException.class)
	public ResponseEntity<ErrorResponse> invalidPaginationsState(InvalidPaginationRequestException e){
		return ResponseEntity.badRequest().body(new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.toString(),e.message));
		
		
	}
	

}
