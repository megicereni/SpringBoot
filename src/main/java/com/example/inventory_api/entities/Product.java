package com.example.inventory_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name can't be null")
    @Size(min = 2,max = 100)
    @Column(name = "name")
    private String name;

    @NotNull(message = "Sku can't be null")
    @Column(name = "sku")
    private String sku;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;


    @Column(name = "quantity")
    private int quantity;

    @CreationTimestamp
    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "is_Featured")
    private Boolean isFeatured;

    @ManyToOne
    @JoinColumn(name = "brandID")
    private Brand brand;
}
