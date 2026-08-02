package com.crop.inventory_service.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.crop.inventory_service.dto.ProductFilterRequest;
import com.crop.inventory_service.dto.ProductResponse;
//import com.crop.inventory_service.service.dto.ProductFilterRequest;

@FeignClient(name="PRODUCT-SERVICE")
public interface ProductClient {
	
	@GetMapping("/products/farmerProdcut/{email}")
	public ResponseEntity<List<ProductResponse>> getProductIdsByEmail(@PathVariable String email);
	
	@GetMapping("/products/search")
	public ResponseEntity<List<ProductResponse>> serachProductByName(@RequestParam String name);
	
	@GetMapping("/products/category/{category}")
	public ResponseEntity<List<ProductResponse>> serachProductByCategory(@PathVariable String category);

	@GetMapping("/products/filter")
    public ResponseEntity<List<Long>> filterProductId(@SpringQueryMap ProductFilterRequest request);
}
