package com.dev.order_service.listener;

import com.dev.order_service.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private OrderService orderService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "payment-processed", groupId = "order-service")
    public void handlePaymentProcessed(String message) throws JsonProcessingException {
        orderService.orderProcessed(message);
    }

    @KafkaListener(topics = "payment-failed", groupId = "order-service")
    public void handlePaymentFailure(String message) throws JsonProcessingException {
        orderService.orderRollback(message);
        kafkaTemplate.send("order-failed", message);
    }

}
