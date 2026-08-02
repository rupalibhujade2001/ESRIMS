package com.crop.inventory_service.dto;

import lombok.Builder;

@Builder
public record InverntoryResponse(Long id,Long ProductId,Long AvailableQUantity,Long reservedQuantity) {
	

}
