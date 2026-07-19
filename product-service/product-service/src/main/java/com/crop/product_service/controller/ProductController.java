package com.crop.product_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crop.product_service.dto.ProductRequest;
import com.crop.product_service.dto.ProductResponse;
import com.crop.product_service.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService prodcutService;

	@PostMapping("/createProduct")
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(prodcutService.createProduct(request));
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> findAllProducts() {
		prodcutService.getAllProducts();
		return ResponseEntity.ok(prodcutService.getAllProducts());

	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@Valid @PathVariable Long id) {
		return ResponseEntity.ok(prodcutService.findProductById(id));

	}

	@GetMapping("/category/{Category}")
	public ResponseEntity<List<ProductResponse>> getProductByCategory(@Valid @PathVariable String Category) {
		return ResponseEntity.ok(prodcutService.getProductByCategory(Category));
	}

	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProduct(@Valid @RequestParam String name) {

		return ResponseEntity.ok(prodcutService.searchProductByName(name));

	}

	@GetMapping("/me")
	public ResponseEntity<List<ProductResponse>> myProducts() {
		return ResponseEntity.ok(prodcutService.getMyProducts());

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@Valid @PathVariable Long id, @RequestBody ProductRequest request){
	return ResponseEntity.ok(prodcutService.updateProduct(id,request));	
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteProduct(@Valid @PathVariable Long id, @RequestBody ProductRequest request){
	return ResponseEntity.ok(prodcutService.deleteProduct(id));	
	}

}
