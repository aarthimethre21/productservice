package com.microservice.productservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryQuantityRequest {

    @NotNull
    @Positive
    private Integer quantity;
}
