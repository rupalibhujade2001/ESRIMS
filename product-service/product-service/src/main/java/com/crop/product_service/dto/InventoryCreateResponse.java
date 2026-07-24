package com.crop.product_service.dto;

import java.time.LocalDateTime;

public record InventoryCreateResponse(Long id,Long availableQuantity,Long product_id,Long reserved_quantity,LocalDateTime updatedAt) {

}
