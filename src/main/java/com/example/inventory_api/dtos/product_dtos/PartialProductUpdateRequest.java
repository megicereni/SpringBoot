package com.example.inventory_api.dtos.product_dtos;

import lombok.Data;

@Data
public class PartialProductUpdateRequest {
    private int quantity;
}
