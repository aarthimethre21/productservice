package com.microservice.productservice.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends RouteBuilder {


    @Override
    public void configure() {

        from("direct:product-created")
                .routeId("product-created-route")
                .log("Product created: ${body}");

        from("direct:product-updated")
                .routeId("product-updated-route")
                .log("Product updated: ${body}");

        from("direct:product-status-changed")
                .routeId("product-status-route")
                .log("Product status changed: ${body}");

        from("direct:inventory-reserved")
                .routeId("inventory-reserved-route")
                .log("Inventory reserved: ${body}");

        from("direct:inventory-released")
                .routeId("inventory-released-route")
                .log("Inventory released: ${body}");
    }


    @Bean
    public ApplicationRunner verifyCamelRoutes(
            CamelContext camelContext) {

        return args -> {
            System.out.println("Registered Camel route IDs:");

            camelContext.getRoutes()
                    .forEach(route ->
                            System.out.println(" - " + route.getId())
                    );
        };
    }

}