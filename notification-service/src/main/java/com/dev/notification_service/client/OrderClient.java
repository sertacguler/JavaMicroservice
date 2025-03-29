package com.dev.notification_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "http://localhost:8081/api/orders")
public interface OrderClient {

    @GetMapping("/{orderId}")
    Object getOrderDetails(@PathVariable("orderId") Long orderId);
}
