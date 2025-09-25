package com.example.inventory_api.controllers;

import com.example.inventory_api.dtos.product_dtos.*;
import com.example.inventory_api.entities.Product;
import com.example.inventory_api.exceptions.ConflictException;
import com.example.inventory_api.exceptions.NotFoundException;
import com.example.inventory_api.exceptions.UnprocessableEntityException;
import com.example.inventory_api.mappers.ProductMapper;
import com.example.inventory_api.repositories.BrandRepository;
import com.example.inventory_api.repositories.ProductRepository;
import com.example.inventory_api.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private ProductService productService;


    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductDto productDto = productService.createProduct(request);

        return ResponseEntity.ok(productDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var productDto = productService.getProduct(id);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> sortProduct(@RequestParam(required = false) BigDecimal price) {

        var sortByPrice = productRepository.findAll(Sort.by("price").ascending());


        List<ProductDto> productDtosPrice = productMapper.toDto(sortByPrice);


        return ResponseEntity.ok(productDtosPrice);


    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDto>> filterProduct(
            @RequestParam(required = false) Long brandId) {
        var productDtos = productService.filterProduct(brandId);

        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/filter/name")
    public ResponseEntity<List<ProductDto>> filterByChar(@RequestParam(required = false) String q) {
        var productsDto = productService.filterByChar(q);
        return ResponseEntity.ok(productsDto);

    }

    @GetMapping("/filter/price")
    public ResponseEntity<List<ProductDto>> filterByMinAndMaxPrice(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        var pDto = productService.filterByMinAndMaxPrice(minPrice, maxPrice);

        return ResponseEntity.ok(pDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateProductResponse> updateProduct(@PathVariable Long id, @RequestBody FullProductUpdate productUpdate) {
        var productResponse = productService.updateProduct(id, productUpdate);

        return ResponseEntity.ok(productResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partialUpdate(@PathVariable Long id, @RequestBody PartialProductUpdateRequest request) {

        productService.partialUpdate(id, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{brandID}/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long brandID, @PathVariable Long productId) {
        productService.deleteProduct(brandID, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/sell")
    public ResponseEntity<Void> getProductStock(@PathVariable Long id, @RequestBody PartialProductUpdateRequest request) {

        productService.getProductStock(id, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<?> handleException() {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
