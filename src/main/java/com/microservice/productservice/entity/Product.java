package com.microservice.productservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="product", indexes= {
        @Index(name="idx_product_sku",
        columnList ="sku", unique = true)
})

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, unique = true, length = 100)
   private String sku;

   @Column(nullable = false, length = 200)
   private String name;

   @Column(length = 1000)
   private String description;

   @Column(nullable = false, precision = 19, scale = 2)
   private BigDecimal price;

   @Column(nullable = false, length = 3)
   private String currency;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, length =20)
   private ProductStatus status;

   @Column(name = "categoryId")
   private Long categoryId;

   // Added relationship — read-only mapping
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(
           name = "categoryId",
           referencedColumnName = "id",
           insertable = false,
           updatable = false,
           foreignKey = @ForeignKey(name = "fk_product_category")
   )
   private ProductCategory category;

   @Column(name="createdAt", nullable = false)
   private LocalDateTime createdAt;

   @Column(name="updatedAt", nullable = false)
   private LocalDateTime updatedAt;

   @PrePersist
   public void prePersist(){
      LocalDateTime now = LocalDateTime.now();
      createdAt=now;
      updatedAt=now;
      if(status == null){
         status=ProductStatus.ACTIVE;
      }
   }
@PreUpdate
   public void preUpdate(){
      updatedAt=LocalDateTime.now();
}
}
