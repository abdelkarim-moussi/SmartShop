package com.app.smartshop.domain.model;
import com.app.smartshop.domain.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String userName;
    private String hashedPassword;
    private UserRole role;
}
