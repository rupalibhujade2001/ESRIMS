package com.crop.inventory_service.security.securityUtils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.crop.inventory_service.dto.InventoryStatistics;
import com.crop.inventory_service.entity.Inventory;

class InventoryAnalyticsUtilTest {

	@Test
	void calculatesStockMetricsAndSeparatesLowStockFromOutOfStock() {
		List<Inventory> inventories = List.of(
				inventory(1L, 25L),
				inventory(2L, 5L),
				inventory(3L, 0L));

		InventoryStatistics statistics = InventoryAnalyticsUtil.calculateStatistics(inventories);

		assertThat(statistics.totalProducts()).isEqualTo(3L);
		assertThat(statistics.totalAvailableStock()).isEqualTo(30L);
		assertThat(statistics.lowStockProducts()).isEqualTo(1L);
		assertThat(statistics.outOfStockProducts()).isEqualTo(1L);
		assertThat(statistics.averageStock()).isEqualTo(10.0);
		assertThat(statistics.maximumStock()).isEqualTo(25L);
		assertThat(statistics.minimumStock()).isZero();
	}

	@Test
	void returnsZeroValuesForEmptyInventory() {
		InventoryStatistics statistics = InventoryAnalyticsUtil.calculateStatistics(List.of());

		assertThat(statistics.totalProducts()).isZero();
		assertThat(statistics.totalAvailableStock()).isZero();
		assertThat(statistics.lowStockProducts()).isZero();
		assertThat(statistics.outOfStockProducts()).isZero();
		assertThat(statistics.averageStock()).isZero();
		assertThat(statistics.maximumStock()).isZero();
		assertThat(statistics.minimumStock()).isZero();
	}

	private Inventory inventory(Long productId, Long availableQuantity) {
		return Inventory.builder()
				.productId(productId)
				.AvailableQuantity(availableQuantity)
				.reserverdQuantity(0L)
				.build();
	}
}
