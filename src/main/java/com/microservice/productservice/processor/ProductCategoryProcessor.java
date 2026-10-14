package com.microservice.productservice.processor;

import com.microservice.productservice.dto.response.ProductCategoryResponse;
import com.microservice.productservice.mapper.ProductCategoryMapper;
import com.microservice.productservice.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryProcessor {

    private final ProductCategoryRepository categoryRepository;
    private final ProductCategoryMapper categoryMapper;

    public ProductCategoryProcessor(
            ProductCategoryRepository categoryRepository,
            ProductCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}