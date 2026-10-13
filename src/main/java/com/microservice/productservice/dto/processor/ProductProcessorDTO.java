package com.microservice.productservice.dto.processor;
import com.microservice.productservice.dto.request.*;
import com.microservice.productservice.entity.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductProcessorDTO {

    private ProductCreateRequest productCreateRequest;
    private ProductUpdateRequest productUpdateRequest;
    private ProductStatusUpdateRequest productStatusUpdateRequest;
    private Product product;
    private Inventory inventory;
    private ProductCategory productCategory;
    private Long productId;
    private String correlationID;
}
