package com.microservice.productservice.dao;

import com.microservice.productservice.dao.InventoryDAO;
import com.microservice.productservice.entity.Inventory;
import com.microservice.productservice.repository.InventoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InventoryDAOImpl implements InventoryDAO {

    private final InventoryRepository inventoryRepository;

    public InventoryDAOImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> findByProductId(Long productId) {
        return inventoryRepository.findByProduct_Id(productId);
    }

    @Override
    public Optional<Inventory> findByProductIdForUpdate(Long productId) {
        return inventoryRepository.findByProductIdForUpdate(productId);
    }
}