package com.dev.customer_service.service;

import com.dev.customer_service.entity.Customer;
import com.dev.customer_service.entity.OrderHistory;

import java.util.Optional;

public interface CustomerService {
    void saveCustomer(Customer customer);
    void addOrderToCustomer(Long customerId, OrderHistory orderHistory);
    Optional<Customer> getCustomerById(Long customerId); // Bu metodu ekliyoruz

}