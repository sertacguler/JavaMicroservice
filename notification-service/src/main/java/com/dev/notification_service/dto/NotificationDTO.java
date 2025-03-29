package com.dev.notification_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NotificationDTO {

    @NotBlank(message = "Recipient cannot be blank")
    private String recipient;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @Email(message = "Invalid email address")
    private String email;

    public @NotBlank(message = "Recipient cannot be blank") String getRecipient() {
        return recipient;
    }

    public void setRecipient(@NotBlank(message = "Recipient cannot be blank") String recipient) {
        this.recipient = recipient;
    }

    public @NotBlank(message = "Message cannot be blank") String getMessage() {
        return message;
    }

    public void setMessage(@NotBlank(message = "Message cannot be blank") String message) {
        this.message = message;
    }

    public @Email(message = "Invalid email address") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email address") String email) {
        this.email = email;
    }
}
