package com.dev.order_service.service;

import com.dev.order_service.entity.Order;
import com.dev.order_service.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void orderProcessed(String message)throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> event = objectMapper.readValue(message, new TypeReference<>() {});
        Long orderId = ((Number) event.get("orderId")).longValue();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setStatus("COMPLETED");
        orderRepository.save(order);
    }

    @Override
    public void orderRollback(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> event = objectMapper.readValue(message, new TypeReference<>() {});
        Long orderId = ((Number) event.get("orderId")).longValue();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
