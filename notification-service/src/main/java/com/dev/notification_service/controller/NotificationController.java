package com.dev.notification_service.controller;

import com.dev.notification_service.dto.NotificationDTO;
import com.dev.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        // saved and published
        notificationService.saveAndPublishNotification(notificationDTO);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
