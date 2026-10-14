package com.microservice.productservice.dto.request;

import com.microservice.productservice.entity.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public record ProductStatusRequest(
        @NotNull ProductStatus status
) {}