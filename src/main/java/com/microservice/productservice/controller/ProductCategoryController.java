package com.microservice.productservice.controller;

import com.microservice.productservice.dto.response.ProductCategoryResponse;
import com.microservice.productservice.processor.ProductCategoryProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/categories")
public class ProductCategoryController {

    private final ProductCategoryProcessor categoryProcessor;

    public ProductCategoryController(
            ProductCategoryProcessor categoryProcessor) {
        this.categoryProcessor = categoryProcessor;
    }

    @GetMapping
    public List<ProductCategoryResponse> getAll() {
        return categoryProcessor.getAll();
    }
}