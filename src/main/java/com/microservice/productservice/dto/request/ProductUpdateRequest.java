package com.microservice.productservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public record ProductUpdateRequest(

        @NotBlank
        @Size(max = 150)
        String name,

        @Size(max = 1000)
        String description,

        @NotNull
        @DecimalMin(value = "0.00")
        @Digits(integer = 17, fraction = 2)
        BigDecimal price,

        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$")
        String currency,

        @NotNull
        Long categoryId
) {}