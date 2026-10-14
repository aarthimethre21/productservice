package com.microservice.productservice.processor;

import com.microservice.productservice.dto.request.StockQuantityRequest;
import com.microservice.productservice.dto.response.InventoryResponse;
import com.microservice.productservice.entity.Inventory;
import com.microservice.productservice.exception.ResourceNotFoundException;
import com.microservice.productservice.mapper.InventoryMapper;
import com.microservice.productservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryProcessor {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryProcessor(
            InventoryRepository inventoryRepository,
            InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Transactional(readOnly = true)
    public InventoryResponse getStock(Long productId) {
        return inventoryMapper.toResponse(getInventory(productId));
    }

    @Transactional
    public InventoryResponse reserve(
            Long productId,
            StockQuantityRequest request) {

        Inventory inventory = getInventoryForUpdate(productId);
        int quantity = request.quantity();

        if (inventory.getAvailableQuantity() < quantity) {
            throw new IllegalArgumentException(
                    "Insufficient available stock for product ID: " + productId
            );
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity
        );

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + quantity
        );

        return inventoryMapper.toResponse(
                inventoryRepository.save(inventory)
        );
    }

    @Transactional
    public InventoryResponse release(
            Long productId,
            StockQuantityRequest request) {

        Inventory inventory = getInventoryForUpdate(productId);
        int quantity = request.quantity();

        if (inventory.getReservedQuantity() < quantity) {
            throw new IllegalArgumentException(
                    "Cannot release more than the reserved quantity."
            );
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - quantity
        );

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + quantity
        );

        return inventoryMapper.toResponse(
                inventoryRepository.save(inventory)
        );
    }

    private Inventory getInventory(Long productId) {
        return inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found for product ID: " + productId
                ));
    }

    private Inventory getInventoryForUpdate(Long productId) {
        return inventoryRepository.findByProductIdForUpdate(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found for product ID: " + productId
                ));
    }
}