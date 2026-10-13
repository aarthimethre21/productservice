package com.microservice.productservice.mapper;

import com.microservice.productservice.dto.request.ProductCreateRequest;
import com.microservice.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public Product toEntity(ProductCreateRequest request) {

        Product product = new Product();

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCurrency(request.getCurrency());
        product.setStatus(request.getStatus());

        // Preserve your existing categoryID field
        product.setCategoryId(request.getCategoryID());

        return product;
    }

    public ProductCreateRequest toCreateRequest(Product product) {

        ProductCreateRequest request = new ProductCreateRequest();

        request.setSku(product.getSku());
        request.setName(product.getName());
        request.setDescription(product.getDescription());
        request.setPrice(product.getPrice());
        request.setCurrency(product.getCurrency());
        request.setStatus(product.getStatus());

        // Preserve your existing categoryID field
        request.setCategoryID(product.getCategoryId());

        return request;
    }


    public void updateEntity(Product product, Product request) {
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCurrency(request.getCurrency());
        product.setCategoryId(request.getCategoryId());
    }


}
