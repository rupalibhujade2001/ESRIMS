package com.crop.inventory_service.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.crop.inventory_service.dto.ProductResponse;

@FeignClient(name="PRODUCT-SERVICE")
public interface ProductClient {
	
	@GetMapping("/products/farmerProdcut/{email}")
	public ResponseEntity<List<ProductResponse>> getProductIdsByEmail(@PathVariable String email);

}
