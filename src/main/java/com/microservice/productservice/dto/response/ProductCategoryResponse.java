package com.microservice.productservice.dto.response;

public record ProductCategoryResponse(
        Long id,
        String name,
        String description
) {}