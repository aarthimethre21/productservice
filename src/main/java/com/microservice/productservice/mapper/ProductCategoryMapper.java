package com.microservice.productservice.mapper;

import com.microservice.productservice.dto.response.ProductCategoryResponse;
import com.microservice.productservice.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {

    public ProductCategoryResponse toResponse(ProductCategory category) {
        return new ProductCategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}