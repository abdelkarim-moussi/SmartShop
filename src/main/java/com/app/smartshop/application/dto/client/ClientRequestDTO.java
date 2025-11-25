package com.app.smartshop.application.dto.client;

import com.app.smartshop.domain.enums.LoyaltyLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientRequestDTO {
    @NotBlank(message = "email must not be blanc or null")
    private String name;
    @Email(message = "email is required")
    private String email;
}
