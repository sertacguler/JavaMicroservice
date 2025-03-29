package com.dev.order_service.client;

import com.dev.order_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "http://localhost:8085/api/products")
public interface ProductClient {

    @GetMapping("/productCode/{productCode}")
    ProductDTO getProductByCode(@PathVariable("productCode") String productCode);

    @GetMapping("/{productCode}/availability")
    Boolean checkProductAvailability(@PathVariable("productCode") String productCode, @PathVariable("productCode") int requiredQuantity);

    @PostMapping("/{productCode}/reduce-stock")
    boolean reduceStock(@PathVariable String productCode, @RequestParam int quantity);


}