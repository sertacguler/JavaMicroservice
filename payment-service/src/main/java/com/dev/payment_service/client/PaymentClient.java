package com.dev.payment_service.client;

import com.dev.payment_service.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8082/api/payment")
public interface PaymentClient {
    @PostMapping
    String processPayment(@RequestBody Payment payment);
}
