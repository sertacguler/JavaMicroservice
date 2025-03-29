package com.dev.notification_service.listener;

import com.dev.notification_service.dto.NotificationDTO;
import com.dev.notification_service.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class KafkaConsumer {
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "payment-processed", groupId = "notification-service-group")
    public void consumePaymentProcessed(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> event = objectMapper.readValue(message, new TypeReference<>() {});
        Long orderId = ((Number) event.get("orderId")).longValue();
        String productCode = (String) event.get("productCode");
        int quantity = (int) event.get("quantity");
        Number totalPriceNumber = (Number) event.get("totalPrice");
        BigDecimal totalPrice = BigDecimal.valueOf(totalPriceNumber.doubleValue());

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setRecipient("Customer Name"); // Müşteri adı (gerekirse veritabanından alınabilir)
        notificationDTO.setEmail("customer@example.com"); // Müşteri e-posta adresi
        notificationDTO.setMessage("Your order with ID " + orderId);

        System.out.println("Received payment-processed event for order ID: " + orderId);
        notificationService.saveAndPublishNotification(notificationDTO);
    }

    @KafkaListener(topics = "order-failed", groupId = "notification-service-group")
    public void handleOrderFailure(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> event = objectMapper.readValue(message, new TypeReference<>() {});
        Long orderId = ((Number) event.get("orderId")).longValue();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setRecipient("Customer Name"); // Müşteri adı (gerekirse veritabanından alınabilir)
        notificationDTO.setEmail("customer@example.com"); // Müşteri e-posta adresi
        notificationDTO.setMessage("Your order failed with ID " + orderId);

        System.out.println("Received order-failed event for order ID: " + orderId);
        notificationService.saveAndPublishNotification(notificationDTO);
    }

}
