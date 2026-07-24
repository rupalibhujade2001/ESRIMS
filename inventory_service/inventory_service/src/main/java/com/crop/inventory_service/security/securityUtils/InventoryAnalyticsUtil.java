package com.crop.inventory_service.security.securityUtils;

import java.util.List;
import java.util.LongSummaryStatistics;

import com.crop.inventory_service.dto.InventoryStatistics;
import com.crop.inventory_service.entity.Inventory;

public final class InventoryAnalyticsUtil {
	private static final long LOW_STOCK_THRESHOLD = 10;
	private static final long OUT_OF_STOCK_PRODUCT=0;
	
	private InventoryAnalyticsUtil() {
		
	}
	public static List<InventoryStatistics> calculateStatistics( List<Inventory> inventories){
		
		Long totalProducts=inventories.stream().count();
		Long totalAvailableStocks=inventories.stream().mapToLong(Inventory::getAvailableQuantity).sum();
		Long lowStockProducts=inventories.stream().filter(n->n.getAvailableQuantity()<LOW_STOCK_THRESHOLD).count();
		Long outOfStockProducts=inventories.stream().filter(n->n.getAvailableQuantity()<=OUT_OF_STOCK_PRODUCT).count();
        LongSummaryStatistics summaryStatisctics=	inventories.stream().mapToLong(Inventory::getAvailableQuantity).summaryStatistics();
        Double averageStock=summaryStatisctics.getAverage();
        Long maximumStock=summaryStatisctics.getMax();
        Long minimumStock=summaryStatisctics.getMin();
		
		return null;
		
	} 

}
