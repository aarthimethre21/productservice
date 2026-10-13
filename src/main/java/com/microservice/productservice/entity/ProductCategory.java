package com.microservice.productservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="product_category", indexes = {@Index(name="idx_product_category_name",
        columnList = "name", unique = true)})

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length =100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name="createdAt", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
