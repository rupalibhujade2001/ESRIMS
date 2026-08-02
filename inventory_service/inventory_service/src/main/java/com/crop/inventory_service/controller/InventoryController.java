package com.crop.inventory_service.controller;

import java.util.List;

import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;
import com.crop.inventory_service.dto.InvenotrySummaryResponse;
import com.crop.inventory_service.dto.InventoryRequest;
import com.crop.inventory_service.dto.InventorySearchRequest;
import com.crop.inventory_service.dto.InventorySearchResponse;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.dto.StockUpdateRequest;
import com.crop.inventory_service.service.InventorySearchService;
import com.crop.inventory_service.service.InventoryService;
import com.crop.inventory_service.service.ProductClient;
import com.crop.inventory_service.service.exception.InventoryException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

	private final InventoryService inventoryService;
	private final InventorySearchService inventorySearchService;


	@PostMapping("/createInventory")
	public ResponseEntity<InverntoryResponse> createInventory(@Validated @RequestBody InventoryRequest request)
			throws InventoryException {
		System.out.println("Hey we are in createInvenotory");
		return ResponseEntity.ok(inventoryService.createInventory(request));

	}

	@GetMapping("/{productId}")
	public ResponseEntity<InverntoryResponse> getInventoryByProductId(@PathVariable Long productId)
			throws InventoryException {
		return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));

	}

	@PostMapping("add-stock/{productId}")
	public ResponseEntity<InverntoryResponse> addStock(@PathVariable Long productId,
			@RequestBody StockUpdateRequest request) throws InventoryException {
		return ResponseEntity.ok(inventoryService.addStock(productId, request));

	}

	@PostMapping("reduce-stock/{productId}")
	public ResponseEntity<InverntoryResponse> removeStock(@PathVariable Long productId,
			@RequestBody StockUpdateRequest request) throws InventoryException {
		return ResponseEntity.ok(inventoryService.reduceStock(productId, request));

	}

	@GetMapping("/dashboard")
	public ResponseEntity<InvenotoryDashBoardResponse> getDashBoard() {
		return ResponseEntity.ok(inventoryService.getDashboard());
	}

	@GetMapping("/dashboard/summary")
	public ResponseEntity<InvenotrySummaryResponse> getDashBoardSummary() {
		return ResponseEntity.ok(inventoryService.getDashboardSummary());
	}

	@GetMapping("/search/name")
	public ResponseEntity<List<InventorySearchResponse>> searchByName(@RequestParam String name) {

		return ResponseEntity.ok(inventorySearchService.searchByProductName(name));
	}

	@GetMapping("/search/{category}")
	public ResponseEntity<List<InventorySearchResponse>> searchByCategory(@PathVariable String category) {

		return ResponseEntity.ok(inventorySearchService.searchByProductCategory(category));
	}

	@GetMapping("/farmerProdcut/search/{email}")
	public ResponseEntity<List<InventorySearchResponse>> searchByEmail(@PathVariable String email) {

		return ResponseEntity.ok(inventorySearchService.searchProductByEmail(email));
	}

	@GetMapping
	public ResponseEntity<Page<InverntoryResponse>> getInvenotries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,@RequestParam String sortBy,@RequestParam String direction) {

		return ResponseEntity.ok(inventoryService.getInventories(page, size,sortBy,direction));

	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<InventorySearchResponse>> searchInvenotry(InventorySearchRequest request){
		return ResponseEntity.ok(inventorySearchService.searchInventory(request));
		
		
	}
}
