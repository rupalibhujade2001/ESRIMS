package com.crop.inventory_service.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crop.inventory_service.dto.InventoryRequest;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.repository.InventoryRepository;
import com.crop.inventory_service.service.InventoryService;

@ExtendWith(MockitoExtension.class)
public class InventoryTest {
	
	@InjectMocks
	private InventoryService inventoryService;
	
	@Mock
	private InventoryRepository inventoryRepository;
	
	
	@Test
	void createInvenotryTest() {
		
		
		InventoryRequest inv=	InventoryRequest.builder().productId(100L).Quantity(5000L).build();
		InverntoryResponse invres=	InverntoryResponse.builder().id(1L).ProductId(100L).AvailableQUantity(5000L).build();
		
		//when(inventoryRepository.save(inv))
		
		
		
		
		
		
	}
	
	
	

}
