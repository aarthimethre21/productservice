package com.microservice.productservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public record StockQuantityRequest(
        @NotNull
        @Min(1)
        Integer quantity
) {}