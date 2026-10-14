package com.microservice.productservice.dao;

import com.microservice.productservice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    Product save(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findBySku(String sku);

    List<Product> findAll();

    boolean existsBySku(String sku);
}