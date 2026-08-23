package com.crop.inventory_service.service;

import com.crop.inventory_service.dto.InvenotrySummaryResponse;
import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;

public interface InventoryDashboardService {

	InvenotoryDashBoardResponse getDashboard(String email);

	InvenotrySummaryResponse getDashboardSummary(String email);

}
