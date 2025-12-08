package com.app.smartshop.application.dto.client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDTO {
    @NotNull(message = "email must not be null")
    private String name;
    @Email(message = "email is required")
    private String email;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
