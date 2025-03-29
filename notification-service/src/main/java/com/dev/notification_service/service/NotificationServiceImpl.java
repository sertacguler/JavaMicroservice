package com.dev.notification_service.service;

import com.dev.notification_service.dto.NotificationDTO;
import com.dev.notification_service.entity.Notification;
import com.dev.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_NAME = "notification-topic";

    @Override
    public void saveAndPublishNotification(NotificationDTO notificationDTO) {
        // Save notification to database
        Notification notification = new Notification();
        notification.setRecipient(notificationDTO.getRecipient());
        notification.setMessage(notificationDTO.getMessage());
        notification.setEmail(notificationDTO.getEmail());
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        kafkaTemplate.send(TOPIC_NAME, notificationDTO.getMessage());

        // Mock email sending (can integrate with real email service)
        System.out.println("Notification saved and published to topic: " + TOPIC_NAME);
    }
}
