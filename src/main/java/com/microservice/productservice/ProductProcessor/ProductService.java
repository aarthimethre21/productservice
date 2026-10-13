package com.microservice.productservice.ProductProcessor;

import com.microservice.productservice.entity.Inventory;
import com.microservice.productservice.entity.Product;
import com.microservice.productservice.entity.ProductCategory;
import com.microservice.productservice.entity.ProductStatus;
import com.microservice.productservice.mapper.ProductMapper;
import com.microservice.productservice.repository.InventoryRepository;
import com.microservice.productservice.repository.ProductCategoryRepository;
import com.microservice.productservice.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.apache.camel.ProducerTemplate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProducerTemplate producerTemplate;

    public ProductService(
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            ProductCategoryRepository categoryRepository,
            ProductMapper productMapper,
            ProducerTemplate producerTemplate) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.producerTemplate = producerTemplate;
    }

    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.findBySku(product.getSku()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "SKU already exists");
        }

        if (product.getPrice() == null
                || product.getPrice().signum() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Price cannot be negative");
        }

        if (product.getCategoryId() != null) {
            categoryRepository.findById(product.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Category not found"));
        }

        product.setId(null);

        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        Product saved = productRepository.save(product);

        Inventory inventory = new Inventory();
        inventory.setProductId(saved.getId());
        inventory.setAvailableQuantity(0);
        inventory.setReservedQuantity(0);

        Inventory savedInventory = inventoryRepository.save(inventory);

        // Publish product-created event
        producerTemplate.sendBody(
                "direct:product-created", saved);

        // Publish inventory-initialized event
        producerTemplate.sendBody(
                "direct:inventory-created", savedInventory);

        return saved;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"));
    }

    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Transactional
    public Product updateProduct(Long id, Product request) {
        Product product = getProduct(id);

        productRepository.findBySku(request.getSku())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "SKU already exists");
                });

        if (request.getPrice() == null
                || request.getPrice().signum() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid price");
        }

        if (request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Category not found"));
        }

        productMapper.updateEntity(product, request);

        Product updatedProduct = productRepository.save(product);

        producerTemplate.sendBody(
                "direct:product-updated", updatedProduct);

        return updatedProduct;
    }

    @Transactional
    public Product changeStatus(Long id, ProductStatus status) {
        Product product = getProduct(id);
        product.setStatus(status);

        Product updatedProduct = productRepository.save(product);

        producerTemplate.sendBody(
                "direct:product-status-changed", updatedProduct);

        return updatedProduct;
    }

    public List<ProductCategory> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public ProductCategory createCategory(ProductCategory category) {
        ProductCategory savedCategory =
                categoryRepository.save(category);

        producerTemplate.sendBody(
                "direct:product-category-created", savedCategory);

        return savedCategory;

    }
}
