package com.dev.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderService {

    void orderProcessed(String message)throws JsonProcessingException;

    void orderRollback(String message) throws JsonProcessingException;

}
