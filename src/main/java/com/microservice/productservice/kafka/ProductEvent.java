package com.microservice.productservice.kafka;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {

    private String eventId;
    private String eventType;
    private Long productId;
    private String sku;
    private String productName;
    private BigDecimal price;
    private String currency;
    private String status;
    private LocalDateTime eventTime;

}