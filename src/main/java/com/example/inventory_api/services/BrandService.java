package com.example.inventory_api.services;

import com.example.inventory_api.dtos.brand_dtos.BrandDto;
import com.example.inventory_api.dtos.brand_dtos.CreateBrandRequest;
import com.example.inventory_api.dtos.brand_dtos.UpdateBrandRequest;
import com.example.inventory_api.entities.Brand;
import com.example.inventory_api.exceptions.ConflictException;
import com.example.inventory_api.exceptions.NotFoundException;
import com.example.inventory_api.exceptions.NoException;
import com.example.inventory_api.mappers.BrandMapper;
import com.example.inventory_api.repositories.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandService {
    private BrandMapper brandMapper;
    private BrandRepository brandRepository;

    public BrandDto createBrand(CreateBrandRequest brandRequest){
         Brand brand=brandMapper.toEntity(brandRequest);
         brandRepository.save(brand);

         var brandDto=brandMapper.toDto(brand);
         brandDto.setId(brand.getId());
         return brandDto;
     }

     public BrandDto getBrand(Long id){
         var brand=brandRepository.findById(id).orElse(null);
         if(brand==null)
            throw new NotFoundException();

         return brandMapper.toDto(brand);
     }

     public List<BrandDto> getBrandByName(){
         List<Brand> brandFiltering=brandRepository.findAll(Sort.by("name"));
         List<BrandDto> brandDtos= brandMapper.toDto(brandFiltering);
         return brandDtos;
     }

     public UpdateBrandRequest updateBrand( Long id, UpdateBrandRequest request){
         var brand=brandRepository.findById(request.getId()).orElse(null);
         if(brand==null)
             throw new NotFoundException();
         brandMapper.updateBrand(request,brand);
         if(brandRepository.existsByName(request.getName()))
             throw new ConflictException();
         brandRepository.save(brand);
         request.setId(brand.getId());

         return request;
     }

     public void deleteBrand(Long id){
         var brand=brandRepository.findById(id).orElse(null);
         if(brand==null)
             throw new NotFoundException();
         if (brand.getProductList().isEmpty()) {
             brandRepository.deleteById(id);
             throw new NoException();
         }
         throw  new ConflictException();
     }


}
