package com.crop.inventory_service.dto;

import lombok.Builder;

@Builder
public record InventoryRequest(Long productId,Long Quantity) {

}
