package com.example.inventory_api.mappers;

import com.example.inventory_api.dtos.brand_dtos.BrandDto;
import com.example.inventory_api.dtos.brand_dtos.CreateBrandRequest;
import com.example.inventory_api.dtos.brand_dtos.UpdateBrandRequest;
import com.example.inventory_api.entities.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDto toDto(Brand brand);


    Brand toEntity(CreateBrandRequest brandDto);

    void updateBrand(UpdateBrandRequest request , @MappingTarget Brand brand);

    List<BrandDto> toDto(List<Brand> brandFiltering);

}
