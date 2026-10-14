package com.microservice.productservice.controller;

import com.microservice.productservice.dto.request.StockQuantityRequest;
import com.microservice.productservice.dto.response.InventoryResponse;
import com.microservice.productservice.processor.InventoryProcessor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryProcessor inventoryProcessor;

    public InventoryController(InventoryProcessor inventoryProcessor) {
        this.inventoryProcessor = inventoryProcessor;
    }

    @GetMapping("/{productId}")
    public InventoryResponse getStock(
            @PathVariable Long productId) {
        return inventoryProcessor.getStock(productId);
    }

    @PostMapping("/{productId}/reservations")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse reserve(
            @PathVariable Long productId,
            @Valid @RequestBody StockQuantityRequest request) {
        return inventoryProcessor.reserve(productId, request);
    }

    @PostMapping("/{productId}/releases")
    public InventoryResponse release(
            @PathVariable Long productId,
            @Valid @RequestBody StockQuantityRequest request) {
        return inventoryProcessor.release(productId, request);
    }
}