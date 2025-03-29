package com.dev.customer_service.listener;

import com.dev.customer_service.entity.OrderHistory;
import com.dev.customer_service.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class KafkaConsumerService {

    @Autowired
    private CustomerService customerService;

    @KafkaListener(topics = "order-created", groupId = "customer-group")
    public void consumeOrderCreated(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> event = objectMapper.readValue(message, Map.class);

            Long customerId = ((Number) event.get("customerId")).longValue();
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrderId(Long.valueOf(event.get("orderId").toString()));
            orderHistory.setProductCode(event.get("productCode").toString());
            orderHistory.setQuantity(Integer.parseInt(event.get("quantity").toString()));
            orderHistory.setTotalPrice(new BigDecimal(event.get("totalPrice").toString()));

            customerService.addOrderToCustomer(customerId, orderHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
