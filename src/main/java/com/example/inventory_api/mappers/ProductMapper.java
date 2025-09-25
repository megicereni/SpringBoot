package com.example.inventory_api.mappers;

import com.example.inventory_api.dtos.product_dtos.CreateProductRequest;
import com.example.inventory_api.dtos.product_dtos.FullProductUpdate;
import com.example.inventory_api.dtos.product_dtos.ProductDto;
import com.example.inventory_api.dtos.product_dtos.UpdateProductResponse;
import com.example.inventory_api.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(CreateProductRequest request);


    ProductDto toDto(Product product);

    void updateProduct(FullProductUpdate update , @MappingTarget Product product);
    ;

    UpdateProductResponse toUpdate(Product product);


    List<ProductDto> toDto(List<Product> sortByPrice);

    List<ProductDto> toFilterProduct(List<Product> products);
}
