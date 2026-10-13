package com.microservice.productservice.dto.request;

import com.microservice.productservice.entity.ProductStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusUpdateRequest {

    @NonNull
    private ProductStatus status;
}
