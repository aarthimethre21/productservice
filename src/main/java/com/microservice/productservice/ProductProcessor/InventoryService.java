package com.microservice.productservice.ProductProcessor;

import com.microservice.productservice.entity.Inventory;
import com.microservice.productservice.repository.InventoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.apache.camel.ProducerTemplate;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProducerTemplate producerTemplate;

    public InventoryService(
            InventoryRepository inventoryRepository,
            ProducerTemplate producerTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.producerTemplate = producerTemplate;
    }

    public Inventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Inventory not found"));
    }

    @Transactional
    public Inventory reserve(Long productId, Integer quantity) {
        validateQuantity(quantity);

        Inventory inventory = getInventory(productId);

        if (inventory.getAvailableQuantity() < quantity) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Insufficient stock");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity);

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + quantity);

        Inventory savedInventory = inventoryRepository.save(inventory);

        producerTemplate.sendBody(
                "direct:inventory-reserved", savedInventory);

        return savedInventory;
    }

    @Transactional
    public Inventory release(Long productId, Integer quantity) {
        validateQuantity(quantity);

        Inventory inventory = getInventory(productId);

        if (inventory.getReservedQuantity() < quantity) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot release more than reserved quantity");
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - quantity);

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + quantity);

        Inventory savedInventory = inventoryRepository.save(inventory);

        producerTemplate.sendBody(
                "direct:inventory-released", savedInventory);

        return savedInventory;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than zero");
        }
    }
}
