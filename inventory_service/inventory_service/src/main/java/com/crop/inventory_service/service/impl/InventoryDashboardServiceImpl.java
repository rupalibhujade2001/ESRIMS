package com.crop.inventory_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crop.inventory_service.constant.CacheConstants;
import com.crop.inventory_service.dto.InvenotrySummaryResponse;
import com.crop.inventory_service.dto.ProductResponse;
import com.crop.inventory_service.entity.Inventory;
import com.crop.inventory_service.repository.InventoryRepository;
import com.crop.inventory_service.security.securityUtils.JwtUtils;
import com.crop.inventory_service.service.InventoryDashboardService;
import com.crop.inventory_service.service.ProductClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryDashboardServiceImpl implements InventoryDashboardService {
	
	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductClient productClient;
	
	
	@Cacheable(value=CacheConstants.INVENTORY_SUMMARY,key="#email",    cacheManager = "myCacheManager") 
	public InvenotrySummaryResponse getDashboardSummary(String email) {

		
		//List<String> role = jwtUtils.getLoggedInUSerRole();
		ResponseEntity<List<ProductResponse>> ids = productClient.getProductIdsByEmail(email);
		List<ProductResponse> product = ids.getBody();
		List<Long> totalProducts = product.stream().map(n -> n.id()).collect(Collectors.toList());
		List<Inventory> invenotry = inventoryRepository.getByProductIdIn(totalProducts);

		Long productQuantity = invenotry.stream().mapToLong(Inventory::getAvailableQuantity).sum();
		
		return new InvenotrySummaryResponse(product.size(),productQuantity);
	}
}
