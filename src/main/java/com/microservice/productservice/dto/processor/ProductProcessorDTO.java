package com.microservice.productservice.dto.processor;

import com.microservice.productservice.dto.request.*;
import com.microservice.productservice.dto.response.ProductResponse;
import com.microservice.productservice.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductProcessorDTO {

    private ProductCreateRequest createRequest;
    private ProductUpdateRequest updateRequest;
    private ProductStatusRequest statusRequest;

    private Product product;
    private ProductResponse response;

    private Long productId;
    private String correlationId;
}