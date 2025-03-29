package com.dev.payment_service.dto;

import java.math.BigDecimal;

public class PaymentDTO {
    private String orderCode;
    private BigDecimal amount;

    // Getters and Setters
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
