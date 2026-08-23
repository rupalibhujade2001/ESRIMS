package com.crop.inventory_service.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.crop.inventory_service.constant.CacheConstants;
import com.crop.inventory_service.dto.InventoryStatistics;
import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;
import com.crop.inventory_service.dto.InvenotrySummaryResponse;
import com.crop.inventory_service.dto.LowStockProductResponse;
import com.crop.inventory_service.dto.ProductResponse;
import com.crop.inventory_service.entity.Inventory;
import com.crop.inventory_service.repository.InventoryRepository;
import com.crop.inventory_service.security.securityUtils.InventoryAnalyticsUtil;
//import com.crop.inventory_service.service.InventoryDashboardService;
//import com.crop.inventory_service.service.ProductClient;
import com.crop.inventory_service.service.InventoryDashboardService;
import com.crop.inventory_service.service.ProductClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryDashboardServiceImpl implements InventoryDashboardService {

	private static final long LOW_STOCK_THRESHOLD = 10L;

	private final InventoryRepository inventoryRepository;
	private final ProductClient productClient;

	@Override
	@Cacheable(value = CacheConstants.INVENTORY_DASHBOARD, key = "#email", cacheManager = "myCacheManager")
	public InvenotoryDashBoardResponse getDashboard(String email) {
		List<ProductResponse> products = getProductsFor(email);
		Map<Long, ProductResponse> productsById = products.stream()
				.collect(Collectors.toMap(ProductResponse::id, Function.identity()));
		List<Inventory> inventories = inventoryRepository.findByProductIdIn(productsById.keySet().stream().toList());
		InventoryStatistics statistics = InventoryAnalyticsUtil.calculateStatistics(inventories);

		long totalReservedStock = inventories.stream()
				.mapToLong(Inventory::getReserverdQuantity)
				.sum();
		Map<String, Long> availableStockByCategory = inventories.stream()
				.collect(Collectors.groupingBy(
						inventory -> productsById.get(inventory.getProductId()).category(),
						Collectors.summingLong(Inventory::getAvailableQuantity)));
		List<LowStockProductResponse> lowStockItems = inventories.stream()
				.filter(inventory -> inventory.getAvailableQuantity() < LOW_STOCK_THRESHOLD)
				.sorted(Comparator.comparingLong(Inventory::getAvailableQuantity))
				.map(inventory -> toLowStockResponse(inventory, productsById.get(inventory.getProductId())))
				.toList();

		return new InvenotoryDashBoardResponse(
				products.size(),
				statistics.totalAvailableStock(),
				totalReservedStock,
				statistics.lowStockProducts(),
				statistics.outOfStockProducts(),
				statistics.averageStock(),
				statistics.maximumStock(),
				statistics.minimumStock(),
				availableStockByCategory,
				lowStockItems);
	}

	@Override
	@Cacheable(value = CacheConstants.INVENTORY_SUMMARY, key = "#email", cacheManager = "myCacheManager")
	public InvenotrySummaryResponse getDashboardSummary(String email) {
		List<ProductResponse> products = getProductsFor(email);
		long productQuantity = inventoryRepository.findByProductIdIn(
				products.stream().map(ProductResponse::id).toList())
				.stream()
				.mapToLong(Inventory::getAvailableQuantity)
				.sum();

		return new InvenotrySummaryResponse(products.size(), productQuantity);
	}

	private List<ProductResponse> getProductsFor(String email) {
		var response = productClient.getProductIdsByEmail(email);
		return response.getBody() == null ? Collections.emptyList() : response.getBody();
	}

	private LowStockProductResponse toLowStockResponse(Inventory inventory, ProductResponse product) {
		return new LowStockProductResponse(
				inventory.getProductId(),
				product.name(),
				product.category(),
				inventory.getAvailableQuantity(),
				inventory.getReserverdQuantity());
	}
}
