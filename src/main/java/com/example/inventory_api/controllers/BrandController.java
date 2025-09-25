package com.example.inventory_api.controllers;

import com.example.inventory_api.dtos.brand_dtos.BrandDto;
import com.example.inventory_api.dtos.brand_dtos.CreateBrandRequest;
import com.example.inventory_api.dtos.brand_dtos.UpdateBrandRequest;
import com.example.inventory_api.exceptions.ConflictException;
import com.example.inventory_api.exceptions.NotFoundException;
import com.example.inventory_api.exceptions.NoException;
import com.example.inventory_api.mappers.BrandMapper;
import com.example.inventory_api.repositories.BrandRepository;
import com.example.inventory_api.services.BrandService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandController {

    private final BrandRepository brandRepository;
    private BrandMapper brandMapper;
    private BrandService brandService;


    @PostMapping
    public ResponseEntity<BrandDto> createBrand(@Valid @RequestBody CreateBrandRequest brandRequest) {

        BrandDto brandDto = brandService.createBrand(brandRequest);

        return ResponseEntity.ok(brandDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrand(@PathVariable Long id) {
        BrandDto brandDto = brandService.getBrand(id);
        return ResponseEntity.ok(brandDto);
    }

    @GetMapping
    public ResponseEntity<List<BrandDto>> getBrandByName(@RequestParam(required = false) String name) {

        List<BrandDto> brandDtos = brandService.getBrandByName();
        return ResponseEntity.ok(brandDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody UpdateBrandRequest request) {
        brandService.updateBrand(id, request);
        return ResponseEntity.ok(request);

    }

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleBrandNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleUniqueBrandName() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoException.class)
    public ResponseEntity<?> noException() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
