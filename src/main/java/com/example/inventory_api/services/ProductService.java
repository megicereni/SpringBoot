package com.example.inventory_api.services;

import com.example.inventory_api.dtos.product_dtos.*;
import com.example.inventory_api.entities.Product;
import com.example.inventory_api.exceptions.ConflictException;
import com.example.inventory_api.exceptions.NotFoundException;
import com.example.inventory_api.exceptions.UnprocessableEntityException;
import com.example.inventory_api.mappers.ProductMapper;
import com.example.inventory_api.repositories.BrandRepository;
import com.example.inventory_api.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {

    private BrandRepository brandRepository;
    private ProductMapper productMapper;
    private ProductRepository productRepository;

    public ProductDto createProduct(CreateProductRequest request){
        var brand=brandRepository.findById(request.getBrandId()).orElse(null);
        var product= productMapper.toEntity(request);
        product.setBrand(brand);
        if(productRepository.existsBySku(request.getSku()) )
            throw new ConflictException();


        productRepository.save(product);
        brand.getProductList().add(product);
        var productDto=productMapper.toDto(product);
        productDto.setId(product.getId());
        return productDto;
    }

    public ProductDto getProduct(Long id){
        var product=productRepository.findById(id).orElse(null);
        if(product==null)
           throw new NotFoundException();

       return productMapper.toDto(product);
    }

    public List<ProductDto> filterProduct(
             Long brandId){
        List<Product> products=productRepository.findByBrandId(brandId);
        List<ProductDto> productDtos=productMapper.toFilterProduct(products);

        return productDtos;
    }


    public List<ProductDto> filterByChar(String q){
        List<Product> productList=productRepository.findByNameContainingIgnoreCase(q);
        List<ProductDto> productsDto=productMapper.toFilterProduct(productList);
        return productsDto;

    }


    public List<ProductDto> filterByMinAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice){
        List<Product> p=productRepository.filterByPrice(minPrice, maxPrice);
        List<ProductDto> pDto=productMapper.toFilterProduct(p);

        return pDto;
    }


    public UpdateProductResponse updateProduct(Long id,FullProductUpdate productUpdate){
        var brand=brandRepository.findById(productUpdate.getBrandId()).orElse(null);

        var product=productRepository.findById(id).orElse(null);
        product.setBrand(brand);
        productMapper.updateProduct(productUpdate,product);

        productRepository.save(product);
        brand.getProductList().add(product);
        var productResponse=productMapper.toUpdate(product);

        productResponse.setNewProduct(productUpdate);
        ;

        return productResponse;
    }

    public void partialUpdate(Long id, PartialProductUpdateRequest request){
        var product=productRepository.findById(id).orElse(null);
        if(product==null)
            throw new NotFoundException();
        product.setQuantity(request.getQuantity());
        productRepository.save(product);
    }

    public void deleteProduct(Long brandID,Long productId){
        var brand=brandRepository.findById(brandID).orElse(null);
        var product=productRepository.findById(productId).orElse(null);
        if(brand==null && product==null)
            throw new NotFoundException();
        brand.removeProduct(product);
        brandRepository.save(brand);
    }

    public void getProductStock(Long id,PartialProductUpdateRequest request){
        var product=productRepository.findById(id).orElse(null);
        int stock=product.getQuantity()-request.getQuantity();
        if(stock<0)
            throw new UnprocessableEntityException();
        product.setQuantity(stock);
        productRepository.save(product);
    }
    }

