package com.dev.microservice_project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductDTO {

    @NotNull(message = "Product code cannot be null")
    private String productCode;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    private int stock;

    public ProductDTO() {
    }

    // Private constructor to be used by the builder
    private ProductDTO(Builder builder) {
        this.productCode = builder.productCode;
        this.name = builder.name;
        this.price = builder.price;
        this.stock = builder.stock;
    }

    // Builder class
    public static class Builder {
        private String productCode;
        private String name;
        private BigDecimal price;
        private int stock;

        public Builder setProductCode(String productCode) {
            this.productCode = productCode;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder setStock(int stock) {
            this.stock = stock;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(this);
        }
    }


    // Getters and Setters
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
