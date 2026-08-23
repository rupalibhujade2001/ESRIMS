package com.crop.inventory_service.dto;

import java.io.Serializable;

public record LowStockProductResponse(
        Long productId,
        String productName,
        String category,
        Long availableQuantity,
        Long reservedQuantity) implements Serializable {

    private static final long serialVersionUID = 1L;
}
