package com.microservice.productservice.controller;

import com.microservice.productservice.entity.Inventory;
import com.microservice.productservice.ProductProcessor.InventoryService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public Inventory getStock(@PathVariable Long productId) {
        return inventoryService.getInventory(productId);
    }

    @PostMapping("/{productId}/reservations")
    public Inventory reserve(
            @PathVariable Long productId,
            @RequestBody Map<String, Integer> request) {
        return inventoryService.reserve(
                productId, request.get("quantity"));
    }

    @PostMapping("/{productId}/releases")
    public Inventory release(
            @PathVariable Long productId,
            @RequestBody Map<String, Integer> request) {
        return inventoryService.release(
                productId, request.get("quantity"));
    }
}
