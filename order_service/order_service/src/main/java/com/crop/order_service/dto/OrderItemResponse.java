package com.crop.order_service.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
	        Long productId,
	        String farmerEmail,
	        Long quantity,
	        BigDecimal unitPrice,
	        BigDecimal subtotal
	) {
	}


