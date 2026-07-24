package com.crop.inventory_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;
import com.crop.inventory_service.dto.InventoryRequest;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.dto.ProductResponse;
import com.crop.inventory_service.dto.StockUpdateRequest;
import com.crop.inventory_service.entity.Inventory;
import com.crop.inventory_service.repository.InventoryRepository;
import com.crop.inventory_service.security.securityUtils.JwtUtils;
import com.crop.inventory_service.service.InventoryService;
import com.crop.inventory_service.service.ProductClient;
import com.crop.inventory_service.service.exception.InventoryException;
//import com.crop.product_service.repository.ProductRepository;
//import com.crop.product_service.service.AuthClient;
//import com.crop.product_service.service.InvenotryClient;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
    
	@Autowired
	private ProductClient productClient;
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public InverntoryResponse createInventory(InventoryRequest request) throws InventoryException {

		if (request.Quantity() <= 0) {
			throw new InventoryException("Quantity must be greater than zero.", HttpStatus.BAD_REQUEST);
		}
		Inventory inventory = Inventory.builder().AvailableQuantity(request.Quantity()).productId(request.productId())
				.updatedAt(LocalDateTime.now()).reserverdQuantity(0L).build();
		try {
			Inventory response = inventoryRepository.save(inventory);
			return (MapToResponse(response));
		} catch (Exception e) {
			throw new InventoryException(e.getMessage());

		}

	}

	private InverntoryResponse MapToResponse(Inventory response) {
		// TODO Auto-generated method stub
		return new InverntoryResponse(response.getId(), response.getProductId(), response.getAvailableQuantity(),
				response.getReserverdQuantity());
	}

	@Override
	public InverntoryResponse getInventoryByProductId(Long ProductId) throws InventoryException {
		Inventory inventory = inventoryRepository.findByProductId(ProductId)
				.orElseThrow(() -> new InventoryException("Inventory not found", HttpStatus.NOT_FOUND));
		System.out.println(inventory.getAvailableQuantity() + inventory.getProductId());

		return MapToResponse(inventory);
	}

	@Override
	public InverntoryResponse addStock(Long productId, StockUpdateRequest Quantity) throws InventoryException {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new InventoryException("Inventory Not Found", HttpStatus.NOT_FOUND));
		inventory.setAvailableQuantity(inventory.getAvailableQuantity() + Quantity.Quantity());
		inventory.setUpdatedAt(LocalDateTime.now());

		Inventory savedInventory = inventoryRepository.save(inventory);

		return MapToResponse(savedInventory);
	}

	@Override
	public InverntoryResponse reduceStock(Long productId, StockUpdateRequest Quantity) throws InventoryException {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new InventoryException("Inventory Not Found", HttpStatus.NOT_FOUND));

		if (inventory.getAvailableQuantity() < Quantity.Quantity()) {
			throw new InventoryException("insufficient qunatity available", HttpStatus.BAD_REQUEST);
		}
		inventory.setAvailableQuantity(inventory.getAvailableQuantity() - Quantity.Quantity());
		inventory.setUpdatedAt(LocalDateTime.now());

		Inventory savedInventory = inventoryRepository.save(inventory);

		return MapToResponse(savedInventory);
	}

	@Override
	public InvenotoryDashBoardResponse getDashboard() {

		String user = jwtUtils.getLoggedInUser();
		List<String> role = jwtUtils.getLoggedInUSerRole();
		ResponseEntity<List<ProductResponse>> ids = productClient.getProductIdsByEmail(user);
		List<Long> totalProducts = ids.getBody().stream().map(ProductResponse::id).toList();

		System.out.println("Cockroaches your product have been arrived.");
		return null;
	}

}
