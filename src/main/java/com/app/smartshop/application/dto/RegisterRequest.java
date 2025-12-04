package com.app.smartshop.application.dto;

import com.app.smartshop.domain.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "username is requeired")
    private String userName;
    @Size(max = 20,min = 8,message = "password must contain at least 8 characters")
    @NotBlank(message = "password is required")
    private String password;
    @NotNull
    private UserRole role;
}
