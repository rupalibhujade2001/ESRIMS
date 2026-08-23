package com.crop.inventory_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.crop.inventory_service.kafka.event.ProductCreatedEvent;
import com.crop.inventory_service.service.InventoryService;
import com.crop.inventory_service.service.exception.InventoryException;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductEventConsumer {
	
	private final InventoryService inventoryService;
	
	@KafkaListener(topics="product-created", groupId="inventory-group")
	public void consume(ProductCreatedEvent event)  {
			inventoryService.createInventoryForProduct(event);
			System.out.println("Kafka event is consumed");
		
	}

}
