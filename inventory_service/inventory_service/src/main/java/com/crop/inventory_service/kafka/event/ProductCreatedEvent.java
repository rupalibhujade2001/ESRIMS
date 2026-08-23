package com.crop.inventory_service.kafka.event;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {

	    private Long productId;

	    private String name;

	    private String category;
	
	    private Long availableQuantity;
}
