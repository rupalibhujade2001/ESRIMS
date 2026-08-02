package com.crop.inventory_service.service;

//import org.hibernate.query.Page;

//import org.hibernate.query.Page;

import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;
import com.crop.inventory_service.dto.InvenotrySummaryResponse;
import com.crop.inventory_service.dto.InventoryRequest;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.dto.StockUpdateRequest;
import com.crop.inventory_service.service.exception.InventoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

public interface InventoryService {

	InverntoryResponse createInventory(InventoryRequest request) throws  InventoryException;

    InverntoryResponse getInventoryByProductId(Long productId) throws InventoryException;
    
    InverntoryResponse addStock(Long productId,StockUpdateRequest Quantity) throws InventoryException;
    InverntoryResponse reduceStock(Long productId,StockUpdateRequest Quantity) throws InventoryException;
    InvenotoryDashBoardResponse getDashboard();

	InvenotrySummaryResponse getDashboardSummary();
	
	//Page<InverntoryResponse> getInventories(int page,int size);

	Page<InverntoryResponse> getInventories(int page, int size, String sortBy2, String Direction);
    
}
