package com.crop.order_service.dto;
import com.crop.order_service.dto.OrderItemRequest;

import jakarta.validation.Valid;
import java.util.List;

public record CreateOrderRequest(
	
	List<OrderItemRequest> items
) {
}
