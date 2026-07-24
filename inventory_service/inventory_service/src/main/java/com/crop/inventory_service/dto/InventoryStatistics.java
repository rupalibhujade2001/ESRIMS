package com.crop.inventory_service.dto;

public record InventoryStatistics( Long totalProducts,

Long totalAvailableStock,

Long lowStockProducts,

Long outOfStockProducts,

Double averageStock,

Long maximumStock,

Long minimumStock)
 {

}
