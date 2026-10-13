package com.microservice.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="inventory", indexes= {
        @Index(name="idx_inventory_product_id",
                columnList ="productId", unique = true)
})

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long productId;

    // Added: relationship with Product using the existing productId column
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "productId",
            referencedColumnName = "id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_product")
    )
    private Product product;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Column(nullable = false)
    private Integer reservedQuantity;

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void preUpdate()
    {
        updatedAt = LocalDateTime.now();
        if(availableQuantity==null){
            availableQuantity=0;
        }
        if(reservedQuantity==null){
            reservedQuantity=0;
        }
    }
}
