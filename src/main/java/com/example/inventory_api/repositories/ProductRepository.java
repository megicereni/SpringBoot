package com.example.inventory_api.repositories;

import com.example.inventory_api.dtos.product_dtos.ProductDto;
import com.example.inventory_api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select case when count(p) = 0 then true else false end from Product p where p.name = :name")
    boolean isUnique( String name );

    boolean existsBySku(String sku);


    List<Product> findByBrandId(Long brandId);

    List<Product> findByNameContainingIgnoreCase(String q);

    @Query("select p from Product p where p.price between :minPrice and :maxPrice")
   List<Product> filterByPrice(BigDecimal minPrice, BigDecimal maxPrice);
}
