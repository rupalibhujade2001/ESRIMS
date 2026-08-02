package com.crop.inventory_service.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crop.inventory_service.dto.InventorySearchRequest;
import com.crop.inventory_service.dto.InventorySearchResponse;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.dto.ProductFilterRequest;
import com.crop.inventory_service.dto.ProductResponse;
import com.crop.inventory_service.entity.Inventory;
import com.crop.inventory_service.repository.InventoryRepository;
import com.crop.inventory_service.security.securityUtils.InventorySpecification;
import com.crop.inventory_service.service.InventorySearchService;
import com.crop.inventory_service.service.ProductClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventorySearchServiceImpl implements InventorySearchService {

	private final ProductClient productClient;
	private final InventoryRepository inventoryRepository;

	@Override
	public List<InventorySearchResponse> searchByProductName(String name) {
		productClient.serachProductByName(name);

		return buildInventorySearchResponse(productClient.serachProductByName(name).getBody());
	}

	@Override
	public List<InventorySearchResponse> searchByProductCategory(String category) {
		return buildInventorySearchResponse(productClient.serachProductByCategory(category).getBody());
	}

	
	@Override
	public List<InventorySearchResponse> searchProductByEmail(String email) {
		return buildInventorySearchResponse(productClient.getProductIdsByEmail(email).getBody());
	}

	
	private  List<InventorySearchResponse> buildInventorySearchResponse(
	        List<ProductResponse> list){
		
		if(list.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		
		List<Long> ProductId = list.stream().map(ProductResponse::id).toList();
		
		List<Inventory> invenotry = inventoryRepository.findByProductIdIn(ProductId);
		
		Map<Long, Inventory> invenotryMap = invenotry.stream().collect(Collectors.toMap(Inventory::getProductId, Function.identity()));
		
		List<InventorySearchResponse> response=new ArrayList<>();
		for(ProductResponse product:list) {
			Inventory inv = invenotryMap.get(product.id());
			
			Long availableQuantity=inv !=null ? inv.getAvailableQuantity():0L;
			Long reservedQuantity=inv !=null ? inv.getReserverdQuantity():0L;

			response.add(new InventorySearchResponse(product.id(),product.name(),product.category(),availableQuantity,reservedQuantity,product.price(),product.imageUrl()));
		}
		
		return response;
		
	}

	@Override
	public Page<InventorySearchResponse> searchInventory(InventorySearchRequest request) {
	
		Page<Inventory> inventorySearchResult;
		List<Long> ProdcutId = productClient.filterProductId(ProductFilterRequest.builder().name(request.getName()).category(request.getCategory()).build()).getBody();
	   
		Pageable pageable=PageRequest.of(request.getPage(), request.getSize());
		if(ProdcutId==null || ProdcutId.isEmpty()) {
			return Page.empty(pageable);
		}
		Specification<Inventory> specificatin=Specification.where(null);
		specificatin=specificatin.and(InventorySpecification.hasProductIds(ProdcutId));
		specificatin=specificatin.and(InventorySpecification.hasMaximumQuantity(request.getMaxQty()));
		specificatin=specificatin.and(InventorySpecification.hasMinimumQuantity(request.getMinQty()));
		 inventorySearchResult = inventoryRepository.findAll(specificatin,pageable);
		List<InventorySearchResponse> response = inventorySearchResult.getContent().stream().map(this::buildDynamicInventoryResponse).toList();
		
		return new PageImpl<>(
				response,
		        pageable,
		        inventorySearchResult.getTotalElements()
		);
	}

	
	private InventorySearchResponse buildDynamicInventoryResponse(Inventory inventory) {

	    return InventorySearchResponse.builder().productId(inventory.getProductId())
	            .availableQuantity(inventory.getAvailableQuantity())
	            .reservedQuantity(inventory.getReserverdQuantity())
	            .build();
	}
	
}
