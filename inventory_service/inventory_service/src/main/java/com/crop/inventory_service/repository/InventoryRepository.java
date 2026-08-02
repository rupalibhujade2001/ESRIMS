package com.crop.inventory_service.repository;

import java.util.List;
import java.util.Optional;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.crop.inventory_service.dto.ProductResponse;
import com.crop.inventory_service.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,Long>, JpaSpecificationExecutor<Inventory> {

	Optional<Inventory> findByProductId(Long ProductId);
	List<Inventory> findByProductIdIn(List<Long> productIds);
	List<Inventory> getByProductIdIn(List<Long> body);
	//<Pageable> Page<Inventory> findAll(Pageable pageable);
}
