package com.crop.inventory_service.service.exception;

import java.time.LocalDateTime;

import org.apache.http.HttpStatus;

public record ErrorResponse(LocalDateTime timeStamp, String statusCode, String Message) {
	
}
