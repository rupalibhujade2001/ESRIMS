package com.enterprise.auth_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enterprise.auth_service.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException e) {

		ErrorResponse response = new ErrorResponse();

		response.setStatus(HttpStatus.CONFLICT.value());
		response.setLocalDateTime(LocalDateTime.now());
		response.setError(e.getMessage());
		return ResponseEntity.status(e.statusCode).body(response);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotExists(EmailAlreadyExistsException e) {

		ErrorResponse response = new ErrorResponse();

		response.setStatus(HttpStatus.CONFLICT.value());
		response.setLocalDateTime(LocalDateTime.now());
		response.setError(e.getMessage());
		return ResponseEntity.status(e.statusCode).body(response);

	}

}
