package com.app.smartshop.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    @NotBlank(message = "name is required")
    @Size(max = 20,min = 3,message = "username must contain at leat 3 characters")
    private String userName;
    @NotBlank(message = "password is required")
    @Size(max = 20, min = 8, message = "password must contain at least 8 characters")
    @Column(nullable = false)
    private String hashedPassword;
}
