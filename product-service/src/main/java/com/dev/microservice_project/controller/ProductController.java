package com.dev.microservice_project.controller;

import com.dev.microservice_project.dto.ProductDTO;
import com.dev.microservice_project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public List<Map<String, Object>> getPosts() {
        return productService.fetchPosts();
    }

    @PostMapping
    public ResponseEntity<String> createPost(@Valid @RequestBody ProductDTO productDTO) {
        productService.createPost(productDTO);
        return ResponseEntity.ok("Post created successfully");
    }

    @GetMapping("/productCode/{productCode}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable String productCode) {
        ProductDTO product = productService.getProductByCode(productCode);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productCode}/availability")
    public ResponseEntity<Boolean> checkProductAvailability(@PathVariable String productCode, @PathVariable int requiredQuantity) {
        Boolean isAvailable = productService.checkProductAvailability(productCode, requiredQuantity);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping("/{productCode}/reduce-stock")
    public ResponseEntity<Boolean> reduceStock(@PathVariable String productCode, @RequestParam int quantity) {
        boolean success = productService.reduceStock(productCode, quantity);
        return ResponseEntity.ok(success);
    }

}