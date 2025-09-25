package com.example.inventory_api.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 2)
    @Column(name = "name")
    private String name;

    @Size(max = 80,message = "Country's name can't be more than 80 characters" )
    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "brand",orphanRemoval = true)
    private List<Product> productList=new ArrayList<>();



    public void removeProduct(Product product){
        productList.remove(product);
    }

}
