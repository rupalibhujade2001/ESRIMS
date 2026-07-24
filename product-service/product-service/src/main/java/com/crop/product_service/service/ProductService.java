package com.crop.product_service.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.crop.product_service.dto.ProductRequest;
import com.crop.product_service.dto.ProductResponse;

import jakarta.validation.Valid;

public interface ProductService {
	
	
	public ProductResponse createProduct(ProductRequest request);
	
	public List<ProductResponse> getAllProducts();
	//public List<ProductResponse>

	public ProductResponse findProductById(@Valid Long id);

	public List<ProductResponse> getProductByCategory(@Valid String category);

	public List<ProductResponse> searchProductByName(@Valid String name);

	public List<ProductResponse> getMyProducts();

	public ProductResponse updateProduct(Long id,@Valid ProductRequest request);

	public boolean deleteProduct(@Valid Long id);

	public List<ProductResponse>  getMyProductsByEmail(String email);

}
