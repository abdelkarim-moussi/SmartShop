package com.app.smartshop.infrastructure.controller;

import com.app.smartshop.application.dto.client.AuthRequest;
import com.app.smartshop.application.dto.client.AuthResponse;
import com.app.smartshop.application.dto.client.RegisterRequest;
import com.app.smartshop.application.service.AuthService;
import com.app.smartshop.application.util.LoginResult;
import com.app.smartshop.application.util.SessionManager;
import com.app.smartshop.domain.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest){

        String success = authService.register(registerRequest.getUserName(),registerRequest.getPassword(), registerRequest.getRole());

        if(success.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(success);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest,
                                HttpServletRequest request){

        LoginResult result = authService.login(authRequest.getUserName(),authRequest.getPassword(),authRequest.isRememberMe());

        if(result.isFailure()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String cookieId = SessionManager.createSession(request,result);
        return ResponseEntity.ok().body(cookieId);

    }
}
