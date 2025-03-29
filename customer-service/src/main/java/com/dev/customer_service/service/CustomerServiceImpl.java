package com.dev.customer_service.service;

import com.dev.customer_service.entity.Customer;
import com.dev.customer_service.entity.OrderHistory;
import com.dev.customer_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void addOrderToCustomer(Long customerId, OrderHistory orderHistory) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty())
            return;
        Customer customer = customerOptional.get();
        customer.getOrderHistory().add(orderHistory);
        customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId); // Bu kısım eklendi
    }
}
