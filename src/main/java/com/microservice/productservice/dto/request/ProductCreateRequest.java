package com.microservice.productservice.dto.request;


import com.microservice.productservice.entity.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank
    @Size(max=100)
    private String sku;

    @NotBlank
    @Size(max=200)
    private String name;

    @Size(max=1000)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin("0.00") // why can't we use precision = 19, scale = 2
    @Digits(integer =17, fraction=2)
    private BigDecimal price;

    @NotBlank
    @Pattern(regexp="^[A-Z]{3}$")
    private String currency;

    private ProductStatus status;

    @Positive
    private Long categoryID;

}
