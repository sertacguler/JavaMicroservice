package com.dev.notification_service.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @KafkaListener(topics = "notification-topic", groupId = "notification-service-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        // Burada mesajı işleyebilirsiniz
    }
}
