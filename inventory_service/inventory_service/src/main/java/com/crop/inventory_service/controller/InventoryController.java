package com.crop.inventory_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;
import com.crop.inventory_service.dto.InventoryRequest;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.dto.StockUpdateRequest;
import com.crop.inventory_service.service.InventoryService;
import com.crop.inventory_service.service.exception.InventoryException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@PostMapping
	public ResponseEntity<InverntoryResponse> createInventory(@Validated @RequestBody InventoryRequest request) throws InventoryException{
		
	return ResponseEntity.ok(inventoryService.createInventory(request));
		
		
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<InverntoryResponse> getInventoryByProductId(@PathVariable Long productId) throws InventoryException{
		return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
		
		
	}
	
	@PostMapping("add-stock/{productId}")
	public ResponseEntity<InverntoryResponse> addStock(@PathVariable Long productId ,@RequestBody StockUpdateRequest request) throws InventoryException{
		return   ResponseEntity.ok(inventoryService.addStock(productId, request));
		
	}
	
	@PostMapping("reduce-stock/{productId}")
	public ResponseEntity<InverntoryResponse> removeStock(@PathVariable Long productId ,@RequestBody StockUpdateRequest request) throws InventoryException{
		return   ResponseEntity.ok(inventoryService.reduceStock(productId, request));
		
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<InvenotoryDashBoardResponse> getDashBoard(){
		return ResponseEntity.ok(inventoryService.getDashboard());
	}
	
	

}
