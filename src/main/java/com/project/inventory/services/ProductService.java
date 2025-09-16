package com.project.inventory.services;

import com.project.inventory.entities.Brand;
import com.project.inventory.entities.Product;
import com.project.inventory.repositories.BrandRepository;
import com.project.inventory.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private  BrandRepository brandRepository;

    public void createProduct(Product product) {
        productRepository.save(product);
        System.out.println("Saving product...");
    }

    public void sortProducts() {
        Sort sort = Sort.by("price").ascending().and(Sort.by("name"));
        productRepository.findAll(sort).forEach(System.out::println);
    }

    @Transactional
    public void getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow();
        System.out.println(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void filter(Long BrandId) {
        Brand brand = brandRepository.findById(BrandId).orElseThrow();
        List<Product> products = productRepository.findByBrand(brand);
        products.forEach(System.out::println);
    }

    @Transactional
    public void updateProductByPrice(Long id, BigDecimal price) {
        productRepository.partialUpdate(id, price);
    }

    @Transactional
    public void updateProduct(Long id, String newName, String newSku, BigDecimal newPrice, Boolean isFeatured) {
        productRepository.fullUpdate(id, newName, newSku, newPrice, isFeatured);
    }

}
