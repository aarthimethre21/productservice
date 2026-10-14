package com.microservice.productservice.mapper;

import com.microservice.productservice.dto.response.ProductResponse;
import com.microservice.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

//    public Product toEntity(ProductCreateRequest request) {
//        return Product.builder()
//                .sku(request.getSku().trim())
//                .name(request.getName().trim())
//                .description(request.getDescription())
//                .price(request.getPrice())
//                .currency(request.getCurrency())
//                .status(ProductStatus.ACTIVE)
//                .build();
//    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency(),
                product.getStatus(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}