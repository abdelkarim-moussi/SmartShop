package com.app.smartshop.infrastructure.controller;

import com.app.smartshop.application.dto.AuthRequest;
import com.app.smartshop.application.dto.RegisterRequest;
import com.app.smartshop.application.service.IAuthService;
import com.app.smartshop.application.util.LoginResult;
import com.app.smartshop.application.util.SessionManager;
import com.app.smartshop.domain.entity.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest){

        String success = authService.register(registerRequest.getUserName(),registerRequest.getPassword(), registerRequest.getRole(), Client.builder().build());

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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        authService.logout(request,response);

        return ResponseEntity.ok().build();
    }
}
