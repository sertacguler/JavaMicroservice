package com.dev.order_service.client;

import com.dev.order_service.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:8083/api/notifications")
public interface NotificationClient {
    @PostMapping
    void sendNotification(@RequestBody NotificationDTO notificationDTO);
}