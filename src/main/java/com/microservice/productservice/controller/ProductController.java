package com.microservice.productservice.controller;
import com.microservice.productservice.dto.request.ProductCreateRequest;
import com.microservice.productservice.entity.Product;
import com.microservice.productservice.entity.ProductCategory;
import com.microservice.productservice.entity.ProductStatus;
import com.microservice.productservice.mapper.ProductMapper;
import com.microservice.productservice.ProductProcessor.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    public ProductController(
            ProductService productService,
            ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public Product create(
            @Valid @RequestBody ProductCreateRequest request) {

        Product product = productMapper.toEntity(request);

        return productService.createProduct(product);
    }
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/sku/{sku}")
    public Product getBySku(@PathVariable String sku) {
        return productService.getProductBySku(sku);
    }

    @PutMapping("/{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @PatchMapping("/{id}/status")
    public Product changeStatus(
            @PathVariable Long id,
            @RequestBody ProductStatus status) {
        return productService.changeStatus(id, status);
    }

    @GetMapping("/categories")
    public List<ProductCategory> getCategories() {
        return productService.getCategories();
    }

    @PostMapping("/categories")
    public ProductCategory createCategory(
            @RequestBody ProductCategory category) {
        return productService.createCategory(category);
    }
}
