package com.example.inventory_api.repositories;

import com.example.inventory_api.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand,Long> {
    boolean existsByName(String name);
}
