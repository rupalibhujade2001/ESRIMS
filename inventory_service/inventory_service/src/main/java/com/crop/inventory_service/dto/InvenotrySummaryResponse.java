package com.crop.inventory_service.dto;

import java.io.Serializable;

public record InvenotrySummaryResponse( int totalProducts,
        Long totalAvailableStock) implements Serializable{

    private static final long serialVersionUID = 1L;

}
