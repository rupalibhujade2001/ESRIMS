package com.crop.product_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crop.product_service.dto.InventoryCreateRequest;
import com.crop.product_service.dto.InventoryCreateResponse;

@FeignClient(name="INVENTORY-SERVICE")
public interface InvenotryClient {
	
	@PostMapping("/inventory")
	public InventoryCreateResponse createInventory(@RequestBody InventoryCreateRequest request);

}
