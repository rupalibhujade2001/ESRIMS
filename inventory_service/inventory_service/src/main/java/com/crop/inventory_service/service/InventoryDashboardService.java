package com.crop.inventory_service.service;

import com.crop.inventory_service.dto.InvenotrySummaryResponse;

public interface InventoryDashboardService {

	public  InvenotrySummaryResponse getDashboardSummary(String email);

}
