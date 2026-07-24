package com.crop.inventory_service.repository;

import java.util.List;
import java.util.Optional;

//import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.crop.inventory_service.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

	Optional<Inventory> findByProductId(Long ProductId);
	List<Inventory> findByProductIdIn(List<Long> productIds);

}
