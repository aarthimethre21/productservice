package com.microservice.productservice.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends RouteBuilder {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String kafkaBrokers;

    @Override
    public void configure() {

        // Product created
        from("direct:product-created")
                .routeId("product-created-route")
                .log("Product created event received: ${body}")
                .to("kafka:product.event?brokers=" + kafkaBrokers);

        // Product updated
        from("direct:product-updated")
                .routeId("product-updated-route")
                .log("Product updated event received: ${body}")
                .to("kafka:product.event?brokers=" + kafkaBrokers);

        // Product status changed
        from("direct:product-status-changed")
                .routeId("product-status-changed-route")
                .log("Product status changed event received: ${body}")
                .to("kafka:product.event?brokers=" + kafkaBrokers);

        // Product category created
        from("direct:product-category-created")
                .routeId("product-category-created-route")
                .log("Product category created event received: ${body}")
                .to("kafka:product-category.event?brokers=" + kafkaBrokers);

        // Inventory created
        from("direct:inventory-created")
                .routeId("inventory-created-route")
                .log("Inventory created event received: ${body}")
                .to("kafka:inventory.event?brokers=" + kafkaBrokers);

        // Inventory reserved
        from("direct:inventory-reserved")
                .routeId("inventory-reserved-route")
                .log("Inventory reserved event received: ${body}")
                .to("kafka:inventory.event?brokers=" + kafkaBrokers);

        // Inventory released
        from("direct:inventory-released")
                .routeId("inventory-released-route")
                .log("Inventory released event received: ${body}")
                .to("kafka:inventory.event?brokers=" + kafkaBrokers);
    }

    @Bean
    public ApplicationRunner verifyCamelRoutes(
            CamelContext camelContext) {

        return args -> {
            System.out.println("Registered Product Service Camel route IDs:");

            camelContext.getRoutes()
                    .forEach(route ->
                            System.out.println(" - " + route.getId())
                    );
        };
    }
}