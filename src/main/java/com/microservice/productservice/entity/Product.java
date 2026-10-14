package com.microservice.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products",
        uniqueConstraints = {@UniqueConstraint(name = "uk_product_sku", columnNames = "sku")},
        indexes = {
                @Index(name = "idx_product_name", columnList = "name"),
                @Index(name = "idx_product_status", columnList = "status"),
                @Index(name = "idx_product_category", columnList = "category_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, length = 50)
   private String sku;

   @Column(nullable = false, length = 150)
   private String name;

   @Column(length = 1000)
   private String description;

   @Column(nullable = false, precision = 19, scale = 2)
   private BigDecimal price;

   @Column(nullable = false, length = 3)
   private String currency;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, length = 20)
   @Builder.Default
   private ProductStatus status = ProductStatus.ACTIVE;

   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;

   @Column(nullable = false)
   private LocalDateTime updatedAt;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(
           name = "category_id",
           nullable = false,
           foreignKey = @ForeignKey(name = "fk_product_category")
   )
   private ProductCategory category;

   @OneToOne(
           mappedBy = "product",
           cascade = CascadeType.ALL,
           fetch = FetchType.LAZY,
           orphanRemoval = true
   )
   private Inventory inventory;

   public void setInventory(Inventory inventory) {
      this.inventory = inventory;

      if (inventory != null) {
         inventory.setProduct(this);
      }
   }

   @PrePersist
   protected void onCreate() {
      LocalDateTime now = LocalDateTime.now();

      if (createdAt == null) {
         createdAt = now;
      }

      updatedAt = now;
   }

   @PreUpdate
   protected void onUpdate() {
      updatedAt = LocalDateTime.now();
   }
}