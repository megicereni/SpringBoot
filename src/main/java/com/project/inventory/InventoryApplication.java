package com.project.inventory;

import com.project.inventory.entities.Brand;
import com.project.inventory.entities.Product;
import com.project.inventory.services.BrandService;
import com.project.inventory.services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(InventoryApplication.class, args);
        BrandService service = context.getBean(BrandService.class);
        ProductService productService = context.getBean(ProductService.class);

        service.deleteBrand(5L);
        service.getBrandByID(6L);
        service.findByCriteria("A");
        service.updateBrand(7L, "Lenovo", "China");
        service.findByCriteria("N");


        productService.getProductById(2L);

        productService.filter(9L);

        productService.updateProductByPrice(2L, BigDecimal.valueOf(19));

        Brand brand = Brand.builder()
                .name("Ray Ban")
                .country(null)
                .build();

        Product product = Product.builder()
                .name("SunGlasses")
                .sku("9898-09")
                .price(BigDecimal.valueOf(100))
                .quantity(20)
                .brand(brand)
                .build();
        brand.addProduct(product);   //NullPointerException
        productService.createProduct(product);


        Brand brand1 = Brand.builder()
                .name("Gucci")
                .country("Italy")
                .build();

        Product product1 = Product.builder()
                .name("Dress")
                .sku("83673-76")
                .price(BigDecimal.valueOf(200.78))
                .quantity(6)
                .build();

        brand1.addProduct(product1);   //NullPointerException

        service.createBrand(brand1);


    }

}
