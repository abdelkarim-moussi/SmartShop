package com.app.smartshop.infrastructure.filter;

import com.app.smartshop.application.util.LoginResult;
import com.app.smartshop.domain.enums.UserRole;
import com.app.smartshop.domain.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@WebFilter("/api/v1/*")
@Component
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();

        if (isPublic(path)){
            filterChain.doFilter(request,response);
            return;
        }

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("details") == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        LoginResult result = (LoginResult) session.getAttribute("details");

        if(!isAuthorized(result.getUser(),path)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request,response);

    }

    private boolean isPublic(String path){
        return path.startsWith("/api/v1/login");
    }

    private boolean isAuthorized(User user,String path){
        return user.getRole().equals(UserRole.ADMIN);
    }

}
