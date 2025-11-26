package com.app.smartshop.application.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionManager {

    public static String createSession(HttpServletRequest request,LoginResult result){
        HttpSession session = request.getSession(true);
        session.setAttribute("details",result);

        int duration = result.isRememberMe()
                ? 7 * 24 * 60 * 60
                : 2 * 60 * 60;

        session.setMaxInactiveInterval(duration);

        Cookie cookie = new Cookie("JSESSIONID",session.getId());
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        ((HttpServletResponse) request).addCookie(cookie);
        return cookie.getValue();
    }
}
