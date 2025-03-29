package com.dev.order_service.client;

import com.dev.order_service.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8082/api/payments")
public interface PaymentClient {
    @PostMapping
    String processPayment(@RequestBody PaymentDTO payment);
}