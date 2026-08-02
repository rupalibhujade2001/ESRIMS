package com.crop.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchRequest {

    private String name;

    private String category;

    private Integer page = 0;

    private Integer size = 10;

    private String sortBy = "updateAt";

    private String direction = "DESC";
}