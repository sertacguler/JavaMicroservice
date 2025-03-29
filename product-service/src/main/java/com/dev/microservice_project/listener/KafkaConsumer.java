package com.dev.microservice_project.listener;

import com.dev.microservice_project.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private ProductService productService;

    @KafkaListener(topics = "order-created", groupId = "product-service-group")
    public void consumeOrderCreated(String message) throws JsonProcessingException {
        productService.consumeOrderCreated(message);
    }

    @KafkaListener(topics = "payment-failed", groupId = "product-service")
    public void handleStockRollback(String message) throws JsonProcessingException {
        productService.handleStockRollback(message);
    }

}
