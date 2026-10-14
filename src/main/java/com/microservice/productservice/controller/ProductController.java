package com.microservice.productservice.controller;

import com.microservice.productservice.dto.request.ProductCreateRequest;
import com.microservice.productservice.dto.request.ProductStatusRequest;
import com.microservice.productservice.dto.request.ProductUpdateRequest;
import com.microservice.productservice.dto.response.ProductResponse;
import com.microservice.productservice.processor.ProductProcessor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductProcessor productProcessor;

    public ProductController(ProductProcessor productProcessor) {
        this.productProcessor = productProcessor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody ProductCreateRequest request) {
        return productProcessor.create(request);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productProcessor.getAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productProcessor.getById(id);
    }

    @GetMapping("/sku/{sku}")
    public ProductResponse getBySku(@PathVariable String sku) {
        return productProcessor.getBySku(sku);
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {
        return productProcessor.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public ProductResponse changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProductStatusRequest request) {
        return productProcessor.changeStatus(id, request);
    }
}