package com.crop.product_service.kafka.Producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.crop.product_service.kafka.event.ProductCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductEventProducer {
	
	private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
	private final String TOPIC="product-created";
	
	public void publishProductCreatedEvent(ProductCreatedEvent event) {
	kafkaTemplate.send(TOPIC,event.getProductId().toString(),event);
        System.out.println("Published Product Created Event : " + event);

		
	}

}
