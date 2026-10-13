package com.microservice.productservice.kafka;

import com.microservice.productservice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductKafkaProducer {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    private static final String TOPIC = "product.event";

    public void publishProductCreated(Product product) {

        ProductEvent event = ProductEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("PRODUCT_CREATED")
                .productId(product.getId())
                .sku(product.getSku())
                .productName(product.getName())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .status(product.getStatus() != null
                        ? product.getStatus().name()
                        : null)
                .eventTime(LocalDateTime.now())
                .build();

        kafkaTemplate.send(
                TOPIC,
                product.getId().toString(),
                event
        );
    }
}