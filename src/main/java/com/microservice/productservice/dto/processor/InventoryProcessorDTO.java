package com.microservice.productservice.dto.processor;

import com.microservice.productservice.dto.request.StockQuantityRequest;
import com.microservice.productservice.dto.response.InventoryResponse;
import com.microservice.productservice.entity.Inventory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryProcessorDTO {

    private Long productId;
    private StockQuantityRequest quantityRequest;
    private Inventory inventory;
    private InventoryResponse response;
    private String correlationId;
}