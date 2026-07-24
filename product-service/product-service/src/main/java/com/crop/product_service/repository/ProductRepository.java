package com.crop.product_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crop.product_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

	boolean existsByNameIgnoreCase(String name);
	List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByEmail(String email);
   // Optional<Product> findById(Long id);

}
