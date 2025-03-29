package com.dev.order_service.controller;

import com.dev.order_service.client.NotificationClient;
import com.dev.order_service.client.PaymentClient;
import com.dev.order_service.client.ProductClient;
import com.dev.order_service.config.OrderNotFoundException;
import com.dev.order_service.dto.OrderDTO;
import com.dev.order_service.dto.ProductDTO;
import com.dev.order_service.entity.Order;
import com.dev.order_service.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderDTO orderDTO) {

        // Fetch product details from Product Service
        ProductDTO product = productClient.getProductByCode(orderDTO.getProductCode());
        if (product == null) {
            return ResponseEntity.badRequest().body("Product not available");
        }

        // Create order
        Order order = new Order();
        order.setCustomerId(orderDTO.getCustomerId());
        order.setProductCode(orderDTO.getProductCode());
        order.setQuantity(orderDTO.getQuantity());
        order.setTotalPrice(product.getPrice().multiply(new BigDecimal(orderDTO.getQuantity())));
        order.setStatus("PENDING");

        orderRepository.save(order);
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("customerId", order.getCustomerId());
            message.put("orderId", order.getId());
            message.put("productCode", order.getProductCode());
            message.put("quantity", order.getQuantity());
            message.put("totalPrice", order.getTotalPrice());

            ObjectMapper objectMapper = new ObjectMapper();
            String messageAsJson = objectMapper.writeValueAsString(message);

            kafkaTemplate.send("order-created", messageAsJson);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Order created and processing started. Order ID: " + order.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while sending Kafka message: " + e.getMessage());
        }
    }

    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        order.setStatus(status);
        orderRepository.save(order);

        return ResponseEntity.ok("Order status updated to: " + status);
    }

}