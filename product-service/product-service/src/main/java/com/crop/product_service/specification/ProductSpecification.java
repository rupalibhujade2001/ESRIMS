package com.crop.product_service.specification;

import org.springframework.data.jpa.domain.Specification;

import com.crop.product_service.entity.Product;

public class ProductSpecification {
	
	
	public static Specification<Product> hasName(String name) {

	    return (root, query, criteriaBuilder) -> {

	        if (name == null || name.isBlank()) {
	            return criteriaBuilder.conjunction();
	        }

	        return criteriaBuilder.like(
	                criteriaBuilder.lower(root.get("name")),
	                "%" + name.toLowerCase() + "%"
	        );
	    };
	}

	
	public static Specification<Product> hasCategory(String category) {

	    return (root, query, criteriaBuilder) -> {

	        if (category == null || category.isBlank()) {
	            return criteriaBuilder.conjunction();
	        }

	        return criteriaBuilder.like(
	                criteriaBuilder.lower(root.get("category")),
	                "%" + category.toLowerCase() + "%"
	        );
	    };
	}
}
