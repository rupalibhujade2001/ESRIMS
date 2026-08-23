package com.crop.order_service.service;

import com.crop.order_service.dto.CreateOrderRequest;
import com.crop.order_service.dto.OrderResponse;

public interface OrderService {
	
	OrderResponse CreateOrder(CreateOrderRequest orderRequest);

}
