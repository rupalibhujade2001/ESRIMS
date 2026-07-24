package com.crop.inventory_service.service.exception;

import org.springframework.http.HttpStatus;

public class InventoryException extends Exception {

	public InventoryException(String message) {
		super(message);
	}

	public InventoryException(String string, HttpStatus notFound) {
		super(string);

	}
	

}
