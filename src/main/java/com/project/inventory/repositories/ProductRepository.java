package com.project.inventory.repositories;

import com.project.inventory.entities.Brand;
import com.project.inventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> , JpaRepository<Product,Long> {

    List<Product> findByBrand(Brand brand);

    List<Product> findBySku(String sku);

    @Modifying
    @Procedure("fullUpdateProduct")
    void fullUpdate(Long id, String newName, String newSku, BigDecimal newPrice,Boolean isFeatured);

    @Modifying
    @Query("update Product p set p.price = :price where p.id= :id")
    void partialUpdate(Long id, BigDecimal price);

    @Query("select p from Product p where p.price between :minPrice and :maxPrice")
    List<Product> findProducts(BigDecimal min,BigDecimal max);

}
