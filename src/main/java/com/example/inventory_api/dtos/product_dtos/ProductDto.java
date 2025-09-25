package com.example.inventory_api.dtos.product_dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
