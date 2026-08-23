package com.crop.inventory_service.security.securityUtils;

import java.util.List;
import java.util.LongSummaryStatistics;

import com.crop.inventory_service.dto.InventoryStatistics;
import com.crop.inventory_service.entity.Inventory;

public final class InventoryAnalyticsUtil {
	private static final long LOW_STOCK_THRESHOLD = 10;
	private static final long OUT_OF_STOCK_PRODUCT = 0;

	private InventoryAnalyticsUtil() {
	}

	public static InventoryStatistics calculateStatistics(List<Inventory> inventories) {
		LongSummaryStatistics stockSummary = inventories.stream()
				.mapToLong(Inventory::getAvailableQuantity)
				.summaryStatistics();

		long lowStockProducts = inventories.stream()
				.filter(inventory -> inventory.getAvailableQuantity() > OUT_OF_STOCK_PRODUCT)
				.filter(inventory -> inventory.getAvailableQuantity() < LOW_STOCK_THRESHOLD)
				.count();
		long outOfStockProducts = inventories.stream()
				.filter(inventory -> inventory.getAvailableQuantity() <= OUT_OF_STOCK_PRODUCT)
				.count();

		return new InventoryStatistics(
				(long) inventories.size(),
				stockSummary.getSum(),
				lowStockProducts,
				outOfStockProducts,
				stockSummary.getAverage(),
				stockSummary.getCount() == 0 ? 0L : stockSummary.getMax(),
				stockSummary.getCount() == 0 ? 0L : stockSummary.getMin());
	}
}
