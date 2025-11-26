package com.app.smartshop.application.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "username is requeired")
    private String userName;
    @Size(max = 20,min = 8,message = "password must contain at least 8 characters")
    @NotBlank(message = "password is required")
    private String password;
    @Pattern(regexp = "true|false", message = "remember me must be true or false")
    private boolean rememberMe;
}
