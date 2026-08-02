package com.crop.inventory_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.crop.inventory_service.dto.InventorySearchRequest;
import com.crop.inventory_service.dto.InventorySearchResponse;

public interface InventorySearchService {
	
	List<InventorySearchResponse> searchByProductName(String name);
	List<InventorySearchResponse> searchByProductCategory(String category);
	List<InventorySearchResponse> searchProductByEmail(String email);
    Page<InventorySearchResponse> searchInventory(InventorySearchRequest request);	

}
