package com.microservice.productservice.mapper;

import com.microservice.productservice.dto.response.InventoryResponse;
import com.microservice.productservice.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryResponse toResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getId(),
                inventory.getProduct().getId(),
                inventory.getAvailableQuantity(),
                inventory.getReservedQuantity(),
                inventory.getUpdatedAt()
        );
    }
}