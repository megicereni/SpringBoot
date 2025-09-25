package com.example.inventory_api.dtos.product_dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FullProductUpdate {
    private String name;
    private String sku;
    private BigDecimal price;
    private int quantity;
    private Long brandId;
    private Boolean isFeatured;
}
