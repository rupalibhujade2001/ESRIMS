package com.crop.inventory_service.service;

import com.crop.inventory_service.dto.InvenotoryDashBoardResponse;
import com.crop.inventory_service.dto.InventoryRequest;
import com.crop.inventory_service.dto.InverntoryResponse;
import com.crop.inventory_service.dto.StockUpdateRequest;
import com.crop.inventory_service.service.exception.InventoryException;

public interface InventoryService {

	InverntoryResponse createInventory(InventoryRequest request) throws  InventoryException;

    InverntoryResponse getInventoryByProductId(Long productId) throws InventoryException;
    
    InverntoryResponse addStock(Long productId,StockUpdateRequest Quantity) throws InventoryException;
    InverntoryResponse reduceStock(Long productId,StockUpdateRequest Quantity) throws InventoryException;
    InvenotoryDashBoardResponse getDashboard();
    
}
