package com.app.smartshop.application.service;

import com.app.smartshop.application.util.LoginResult;
import com.app.smartshop.domain.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    LoginResult login(String userName,String password,boolean rememberMe);
    String register(String userName, String password, UserRole role);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
