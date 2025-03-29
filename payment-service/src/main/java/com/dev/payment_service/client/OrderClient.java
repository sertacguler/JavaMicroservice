package com.dev.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "http://localhost:8081/api/orders")
public interface OrderClient {
    @PutMapping("/{orderId}/status/{status}")
    void updateOrderStatus(@PathVariable("orderId") Long orderId, @PathVariable("status") String status);
}
