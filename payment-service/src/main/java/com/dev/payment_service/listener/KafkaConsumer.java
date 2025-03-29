package com.dev.payment_service.listener;

import com.dev.payment_service.entity.Payment;
import com.dev.payment_service.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class KafkaConsumer {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "stock-reduced", groupId = "payment-service-group")
    public void consumeStockReduced(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> event = objectMapper.readValue(message, new TypeReference<>() {});
        Long orderId = ((Number) event.get("orderId")).longValue();
        String productCode = (String) event.get("productCode");
        int quantity = (int) event.get("quantity");
        Number totalPriceNumber = (Number) event.get("totalPrice");
        BigDecimal totalPrice = BigDecimal.valueOf(totalPriceNumber.doubleValue());

        System.out.println("Received stock-reduced event for orderID: " + orderId);

        // Convert PaymentDTO to Payment Entity
        Payment payment = new Payment();
        payment.setOrderCode(productCode);
        payment.setAmount(totalPrice);
        payment.setStatus("SUCCESS");
        Payment paymentProcessed = paymentService.processPayment(payment);
        if (paymentProcessed != null) {
            kafkaTemplate.send("payment-processed", message);
        } else {
            kafkaTemplate.send("payment-failed", message);
        }
    }
}
