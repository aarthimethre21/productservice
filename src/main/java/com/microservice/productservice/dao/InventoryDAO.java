package com.microservice.productservice.dao;

import com.microservice.productservice.entity.Inventory;

import java.util.Optional;

public interface InventoryDAO {

    Inventory save(Inventory inventory);

    Optional<Inventory> findByProductId(Long productId);

    Optional<Inventory> findByProductIdForUpdate(Long productId);
}