package com.dev.microservice_project.repository;

import com.dev.microservice_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    // Check if product is available in stock
    Optional<Product> findByProductCodeAndStockGreaterThan(String productCode, int stock);

}
