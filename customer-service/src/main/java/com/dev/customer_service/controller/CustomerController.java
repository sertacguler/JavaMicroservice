package com.dev.customer_service.controller;

import com.dev.customer_service.entity.Customer;
import com.dev.customer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return ResponseEntity.ok("Customer created successfully");
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<?> getCustomerOrders(@PathVariable Long customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer.get().getOrderHistory());
    }
}
