package com.dev.payment_service.controller;

import com.dev.payment_service.client.OrderClient;
import com.dev.payment_service.dto.PaymentDTO;
import com.dev.payment_service.entity.Payment;
import com.dev.payment_service.service.PaymentService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody PaymentDTO paymentDTO) {

        // Convert PaymentDTO to Payment Entity
        Payment payment = new Payment();
        payment.setOrderCode(paymentDTO.getOrderCode());
        payment.setAmount(paymentDTO.getAmount());
        payment.setStatus("SUCCESS");

        paymentService.processPayment(payment);

        kafkaTemplate.send("payment-processed", payment.getStatus() + ":" + paymentDTO.getOrderCode());

        try {
            // Notify Order Service
            orderClient.updateOrderStatus(Long.valueOf(payment.getOrderCode()), payment.getStatus());
        } catch (FeignException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found: " + payment.getOrderCode());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the payment");
        }

        return ResponseEntity.ok("SUCCESS"); // Payment processed successfully
    }

}
