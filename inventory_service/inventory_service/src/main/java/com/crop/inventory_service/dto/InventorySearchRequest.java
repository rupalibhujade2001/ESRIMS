package com.crop.inventory_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventorySearchRequest
{
	private String name;


private String category;

private Long minQty;

private Long maxQty;

private Integer page;

private Integer size;

private String sortBy;

private String direction;

}
