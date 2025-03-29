package com.dev.payment_service.service;

import com.dev.payment_service.entity.Payment;
import com.dev.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(Payment payment) {
        payment.setStatus("SUCCESS");
        return paymentRepository.save(payment);
    }
}