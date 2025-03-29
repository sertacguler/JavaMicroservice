package com.dev.payment_service.service;

import com.dev.payment_service.entity.Payment;

public interface PaymentService {
    Payment processPayment(Payment payment);
}