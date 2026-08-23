package com.crop.inventory_service.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crop.inventory_service.constant.CacheConstants;
//import com.crop.inventory_service.constant.CacheConstants;
import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;
import com.crop.inventory_service.dto.InvenotrySummaryResponse;
import com.crop.inventory_service.dto.InventoryRequest;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.dto.ProductResponse;
import com.crop.inventory_service.dto.StockUpdateRequest;
import com.crop.inventory_service.entity.Inventory;
import com.crop.inventory_service.kafka.event.ProductCreatedEvent;
import com.crop.inventory_service.repository.InventoryRepository;
import com.crop.inventory_service.security.securityUtils.JwtUtils;
import com.crop.inventory_service.service.InventoryDashboardService;
import com.crop.inventory_service.service.InventoryService;
import com.crop.inventory_service.service.ProductClient;
import com.crop.inventory_service.service.exception.InvalidPaginationRequestException;
import com.crop.inventory_service.service.exception.InventoryException;
//import com.crop.product_service.repository.ProductRepository;
//import com.crop.product_service.service.AuthClient;
//import com.crop.product_service.service.InvenotryClient;

//import jakarta.persistence.Cacheable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductClient productClient;
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private InventoryDashboardService inventoryDashboardService;

	@CacheEvict(value = { CacheConstants.INVENTORY_SUMMARY, CacheConstants.INVENTORY_DASHBOARD }, allEntries = true)
	@Override
	public InverntoryResponse createInventory(InventoryRequest request) throws InventoryException {

		if (request.Quantity() <= 0) {
			throw new InventoryException("Quantity must be greater than zero.", HttpStatus.BAD_REQUEST);
		}
	
		try {
			Inventory response = saveInventory(request.productId(),request.Quantity());
			return (MapToResponse(response));
		} catch (Exception e) {
			throw new InventoryException(e.getMessage());

		}

	}

	private Inventory saveInventory(Long productId, Long quantity) {
		Inventory invenotry= Inventory.builder().AvailableQuantity(quantity).productId(productId)
				.updatedAt(LocalDateTime.now()).reserverdQuantity(0L).build();
		return inventoryRepository.save(invenotry);
		
		
		
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

	@CacheEvict(value = { CacheConstants.INVENTORY_SUMMARY, CacheConstants.INVENTORY_DASHBOARD }, allEntries = true)
	@Override
	public InverntoryResponse addStock(Long productId, StockUpdateRequest Quantity) throws InventoryException {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new InventoryException("Inventory Not Found", HttpStatus.NOT_FOUND));
		inventory.setAvailableQuantity(inventory.getAvailableQuantity() + Quantity.Quantity());
		inventory.setUpdatedAt(LocalDateTime.now());

		Inventory savedInventory = inventoryRepository.save(inventory);

		return MapToResponse(savedInventory);
	}

	@CacheEvict(value = { CacheConstants.INVENTORY_SUMMARY, CacheConstants.INVENTORY_DASHBOARD }, allEntries = true)
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
		return inventoryDashboardService.getDashboard(user);
	}

	@Override
	public InvenotrySummaryResponse getDashboardSummary() {

		String email = jwtUtils.getLoggedInUser();

		return inventoryDashboardService.getDashboardSummary(email);

	}

	@Override
    public Page<InverntoryResponse> getInventories(int page, int size, String sortBy,String direction) {
    	
		Page<Inventory> inventories;
		Pageable pageable;
		try {
			
			if(page<0)
			{
				throw new InvalidPaginationRequestException("page size is less than zero..",HttpStatus.BAD_REQUEST);
			}
			else if(page >100) {
				throw new InvalidPaginationRequestException("page size much more that permitted..",HttpStatus.BAD_REQUEST);
			}
		Sort sort = Sort.by(Direction.fromString(direction), sortBy);
		System.out.println(sortBy);
    	 pageable =PageRequest.of(page, size,sort);
    	 inventories =
    	        inventoryRepository.findAll(pageable);
		}
		catch(RuntimeException e) {
			throw new InvalidPaginationRequestException(e.getMessage(),HttpStatus.BAD_GATEWAY);
		}
    	 List<InverntoryResponse> responses = inventories.getContent()
    	            .stream()
    	            .map(this::buildInventoryResponse)
    	            .toList();

    	    return new PageImpl<>(
    	            responses,
    	            pageable,
    	            inventories.getTotalElements());    }
	
	private InverntoryResponse buildInventoryResponse(Inventory inventory) {

	    return InverntoryResponse.builder()
	            .ProductId(inventory.getProductId())
	            .AvailableQUantity(inventory.getAvailableQuantity())
	            .reservedQuantity(inventory.getReserverdQuantity())
	            .build();
	}

	@Override
	@CacheEvict(value = { CacheConstants.INVENTORY_SUMMARY, CacheConstants.INVENTORY_DASHBOARD }, allEntries = true)
	public void createInventoryForProduct(ProductCreatedEvent event) {
		
		if(inventoryRepository.existsByProductId(event.getProductId()))
		{
			log.info("Product already exists {}",event.getProductId());
			return;
		}
		saveInventory(event.getProductId(),event.getAvailableQuantity());
		
		log.info("Inventory automatically created for product {}",
	            event.getProductId());
	}

}
