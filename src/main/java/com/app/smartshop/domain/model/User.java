package com.app.smartshop.domain.model;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String userName;
    private String hashedPassword;
}
