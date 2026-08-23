package com.crop.inventory_service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record InvenotoryDashBoardResponse(

		long totalProducts,
		long totalAvailableStock,
		long totalReservedStock,
		Long lowStockProducts,
		Long outOfStockProducts,
		Double averageStock,
		Long maximumStock,
		Long minimumStock,
		Map<String, Long> availableStockByCategory,
		List<LowStockProductResponse> lowStockItems

) implements Serializable {

	private static final long serialVersionUID = 1L;
}
