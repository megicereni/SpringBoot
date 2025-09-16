package com.project.inventory.repositories;

import com.project.inventory.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BrandRepository extends CrudRepository<Brand, Long>, JpaRepository<Brand, Long> {

    @Modifying
    @Procedure("fullUpdateBrand")
    void updateBrand(Long id, String newName, String newCountry);

    List<Brand> findByNameStartingWith(String a);


}
