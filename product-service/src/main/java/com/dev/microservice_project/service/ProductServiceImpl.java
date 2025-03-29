package com.dev.microservice_project.service;

import com.dev.microservice_project.dto.ProductDTO;
import com.dev.microservice_project.entity.Product;
import com.dev.microservice_project.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public ProductDTO getProductById(Long id) {
        return new ProductDTO.Builder().setName("Example Product").setPrice(new BigDecimal("100.00")).build();
    }

    @Override
    public List<Map<String, Object>> fetchPosts() {
        // Mock list of posts
        List<Map<String, Object>> posts = new ArrayList<>();
        Map<String, Object> post1 = new HashMap<>();
        post1.put("id", 1);
        post1.put("title", "Sample Post 1");
        posts.add(post1);

        Map<String, Object> post2 = new HashMap<>();
        post2.put("id", 2);
        post2.put("title", "Sample Post 2");
        posts.add(post2);

        return posts;
    }

    @Override
    public void createPost(ProductDTO productDTO) {
        Product product = new Product.Builder()
                .setProductCode(productDTO.getProductCode())
                .setName(productDTO.getName())
                .setStock(productDTO.getStock())
                .setPrice(productDTO.getPrice())
                .build();
        productRepository.save(product);
        System.out.println("Product saved: " + productDTO.getName());
    }

    public ProductDTO getProductByCode(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Product not found for productCode: " + productCode));
        return mapToDTO(product);
    }

    @Override
    public boolean checkProductAvailability(String productCode, int requiredQuantity) {
        return productRepository.findByProductCodeAndStockGreaterThan(productCode, requiredQuantity).isPresent();
    }

    @Override
    public void consumeOrderCreated(String message) throws JsonProcessingException {
        // Mesajı JSON olarak çözümle
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> event = objectMapper.readValue(message, new TypeReference<>() {});
        String productCode = (String) event.get("productCode");
        int quantity = (int) event.get("quantity");

        System.out.println("Received order-created event for ProductCode: " +  productCode + " Quantity: "+ quantity);

        boolean stockReduced = reduceStock(productCode, quantity);
        if (stockReduced) {
            kafkaTemplate.send("stock-reduced", message);
        } else {
            System.out.println("Insufficient stock for ProductCode: " +  productCode + " Quantity: "+ quantity);
        }
    }

    @Override
    public boolean reduceStock(String productCode, int quantity) {
        Optional<Product> productOptional = productRepository.findByProductCode(productCode);

        if (productOptional.isEmpty()) {
            return false;
        }

        Product product = productOptional.get();

        if (product.getStock() >= quantity) {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            return true;
        } else {
            return false; // Yetersiz stok durumu
        }
    }

    @Override
    public void handleStockRollback(String message) throws JsonProcessingException {
        // Mesajı JSON olarak çözümle
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> event = objectMapper.readValue(message, new TypeReference<>() {});
        String productCode = (String) event.get("productCode");
        int quantity = (int) event.get("quantity");

        increaseStock(productCode, quantity);
    }

    @Override
    public void increaseStock(String productCode, int quantity) {
        Optional<Product> productOptional = productRepository.findByProductCode(productCode);
        if (productOptional.isEmpty()) {
            return;
        }
        Product product = productOptional.get();
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO.Builder()
                .setProductCode(product.getProductCode())
                .setName(product.getName())
                .setStock(product.getStock())
                .setPrice(product.getPrice())
                .build();
    }

}
