package com.crop.product_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.crop.product_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product>{

	boolean existsByNameIgnoreCase(String name);
	List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByEmail(String email);
   
    @Query("""
    	       SELECT p.id
    	       FROM Product p
    	       WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
    	       AND (:category IS NULL OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%')))
    	       """)
    List<Long>  findProductIds(String name,String category);
}
