package com.microservice.productservice.dao;

import com.microservice.productservice.dao.ProductCategoryDAO;
import com.microservice.productservice.entity.ProductCategory;
import com.microservice.productservice.repository.ProductCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryDAOImpl implements ProductCategoryDAO {

    private final ProductCategoryRepository categoryRepository;

    public ProductCategoryDAOImpl(
            ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductCategory save(ProductCategory category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<ProductCategory> findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }
}