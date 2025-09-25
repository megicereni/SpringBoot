package com.example.inventory_api.dtos.product_dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateProductResponse {
    private FullProductUpdate newProduct;
    private LocalDateTime updatedAt;
}
