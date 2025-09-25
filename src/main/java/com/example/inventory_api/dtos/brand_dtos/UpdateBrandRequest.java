package com.example.inventory_api.dtos.brand_dtos;

import lombok.Data;

@Data
public class UpdateBrandRequest {
    private Long id;
    private String name;
    private String country;
}
