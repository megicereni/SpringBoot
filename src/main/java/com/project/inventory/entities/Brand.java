package com.project.inventory.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString(exclude = "productList")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Nullable
    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "brand",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Product> productList=new ArrayList<>();

    public Brand(String name, String country ){
        this.name=name;
        this.country=country;
    }

    public void addProduct(Product product){
        productList.add(product);
        product.setBrand(this);
    }

    public void removeProduct(Product product){
        productList.remove(product);
        product.setBrand(null);
    }

}
