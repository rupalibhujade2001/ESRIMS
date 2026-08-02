package com.crop.inventory_service.dto;
import lombok.Builder;

@Builder
public record InventorySearchResponse(    Long productId,

        String productName,

        String category,

        Long availableQuantity,

        Long reservedQuantity,

        Long price,

        String imageUrl
) {

}
