package com.dev.order_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NotificationDTO {

    @NotBlank(message = "Recipient cannot be blank")
    private String recipient;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @Email(message = "Invalid email address")
    private String email;

    // Getters and Setters
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
