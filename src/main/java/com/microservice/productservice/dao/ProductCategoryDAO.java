package com.microservice.productservice.dao;

import com.microservice.productservice.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryDAO {

    ProductCategory save(ProductCategory category);

    Optional<ProductCategory> findById(Long id);

    Optional<ProductCategory> findByName(String name);

    List<ProductCategory> findAll();

    boolean existsByName(String name);
}