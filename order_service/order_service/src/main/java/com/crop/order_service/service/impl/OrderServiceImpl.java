package com.crop.order_service.service.impl;

import org.springframework.stereotype.Service;

import com.crop.order_service.dto.CreateOrderRequest;
import com.crop.order_service.dto.OrderResponse;
import com.crop.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	
	private ProductClient productClient;

	@Override
	public OrderResponse CreateOrder(CreateOrderRequest orderRequest) {
		
		
		return null;
	}

}
