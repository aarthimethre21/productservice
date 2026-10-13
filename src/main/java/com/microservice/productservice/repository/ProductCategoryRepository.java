package com.microservice.productservice.repository;


import com.microservice.productservice.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository
        extends JpaRepository<ProductCategory, Long> {
}
