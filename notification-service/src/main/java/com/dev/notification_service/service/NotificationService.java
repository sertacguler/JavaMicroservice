package com.dev.notification_service.service;

import com.dev.notification_service.dto.NotificationDTO;

public interface NotificationService {
    void saveAndPublishNotification(NotificationDTO notificationDTO);
}
