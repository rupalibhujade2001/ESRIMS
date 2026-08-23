package com.crop.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.crop.order_service.dto.ProductResponse;

@FeignClient("product-service")
public interface ProductClient {

	@GetMapping("/products/{id}")
	public ProductResponse getProduct(@PathVariable Long Id);
}
