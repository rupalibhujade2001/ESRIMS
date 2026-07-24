package com.crop.inventory_service.dto;

public record InvenotoryDashBoardResponse(

		Long getTotalProducts,
		Long getTotalProductsByemail,
		Long getAvailableStockByCategory, 
		Long getTotalProductsByName,
		Long totalAvailableStock,
		Long lowStockProducts,
		Long outOfStockProducts,
		Double averageStock,
		Long maximumStock,
		Long minimumStock

) {

}
