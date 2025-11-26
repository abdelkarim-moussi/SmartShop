package com.app.smartshop.application.util;

import com.app.smartshop.domain.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResult {
    private boolean success;
    private boolean failure;
    private boolean rememberMe;
    private String message;
    private User user;
}
