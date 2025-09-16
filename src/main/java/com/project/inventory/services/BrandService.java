package com.project.inventory.services;

import com.project.inventory.entities.Brand;
import com.project.inventory.entities.Product;
import com.project.inventory.repositories.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BrandService {
    private BrandRepository brandRepository;

    public void createBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void getBrandByID(long id) {
        var brand = brandRepository.findById(id).orElseThrow();
        System.out.println(brand);
    }

    @Transactional
    public void updateBrand(Long id, String newName, String newCountry) {
        brandRepository.updateBrand(id, newName, newCountry);
    }

    @Transactional
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public List<Brand> findByCriteria(String a) {
        List<Brand> brandList = brandRepository.findByNameStartingWith(a);
        brandList.forEach(System.out::println);
        return brandList;
    }

    public void paginatedBrand(int pageNumber, int size) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        Page<Brand> page = brandRepository.findAll(pageRequest);
        List<Brand> brands = page.getContent();
        brands.forEach(System.out::println);
    }


}
