package com.crop.inventory_service.security.securityUtils;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.crop.inventory_service.entity.Inventory;

public class InventorySpecification {

	public static Specification<Inventory> hasProductIds(List<Long> productIds){
		
		return (root,query,criteriaBuilder)->{
			  if (productIds == null) {
		            return criteriaBuilder.conjunction();   // Ignore filter
		        }

		        if (productIds.isEmpty()) {
		            return criteriaBuilder.disjunction();   // No matching products
		        }
			return root.get("productId").in(productIds);
		};
		
	}
	public static Specification<Inventory> hasMinimumQuantity(Long minQty)
	{
		return (root,query,criteriaBuilder)->{
			if(minQty==null) {
				return criteriaBuilder.conjunction();
				
			}
			return criteriaBuilder.greaterThanOrEqualTo(root.get("AvailableQuantity"),minQty);
		};
	}
	public static Specification<Inventory> hasMaximumQuantity(Long maxQty){
		return (root,query,criteriaBuilder)->{ 
			if(maxQty==null) {
				
				return criteriaBuilder.conjunction();
			}
			
			return criteriaBuilder.lessThanOrEqualTo(root.get("AvailableQuantity"), maxQty);
		};
	}

}
