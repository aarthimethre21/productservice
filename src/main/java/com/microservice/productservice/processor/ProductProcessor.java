package com.microservice.productservice.processor;

import com.microservice.productservice.dto.request.ProductCreateRequest;
import com.microservice.productservice.dto.request.ProductStatusRequest;
import com.microservice.productservice.dto.request.ProductUpdateRequest;
import com.microservice.productservice.dto.response.ProductResponse;
import com.microservice.productservice.entity.Inventory;
import com.microservice.productservice.entity.Product;
import com.microservice.productservice.entity.ProductCategory;
import com.microservice.productservice.entity.ProductStatus;
import com.microservice.productservice.exception.DuplicateResourceException;
import com.microservice.productservice.exception.ResourceNotFoundException;
import com.microservice.productservice.mapper.ProductMapper;
import com.microservice.productservice.repository.ProductCategoryRepository;
import com.microservice.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductProcessor {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductProcessor(
            ProductRepository productRepository,
            ProductCategoryRepository categoryRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {

        if (productRepository.existsBySku(request.sku())) {
            throw new DuplicateResourceException(
                    "Product SKU already exists: " + request.sku()
            );
        }

        ProductCategory category = getCategory(request.categoryId());

        Product product = Product.builder()
                .sku(request.sku())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .currency(request.currency())
                .status(ProductStatus.ACTIVE)
                .category(category)
                .build();

        Inventory inventory = Inventory.builder()
                .availableQuantity(request.initialQuantity())
                .reservedQuantity(0)
                .build();

        // Set both sides of the bidirectional relationship.
        product.setInventory(inventory);

        // CascadeType.ALL saves the associated inventory as well.
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return productMapper.toResponse(getProduct(id));
    }

    @Transactional(readOnly = true)
    public ProductResponse getBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with SKU: " + sku
                ));

        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse update(
            Long id,
            ProductUpdateRequest request) {

        Product product = getProduct(id);
        ProductCategory category = getCategory(request.categoryId());

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCurrency(request.currency());
        product.setCategory(category);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changeStatus(
            Long id,
            ProductStatusRequest request) {

        Product product = getProduct(id);
        product.setStatus(request.status());

        return productMapper.toResponse(productRepository.save(product));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with ID: " + id
                ));
    }

    private ProductCategory getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product category not found with ID: " + categoryId
                ));
    }
}