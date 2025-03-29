package com.dev.microservice_project.service;

import com.dev.microservice_project.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductDTO getProductById(Long id);

    List<Map<String, Object>> fetchPosts();

    void createPost(ProductDTO productDTO);

    ProductDTO getProductByCode(String code);

    boolean checkProductAvailability(String code, int requiredQuantity);

    void handleStockRollback(String message) throws JsonProcessingException;

    void consumeOrderCreated(String message) throws JsonProcessingException;

    boolean reduceStock(String productCode, int quantity);

    void increaseStock(String productCode, int quantity);

}
