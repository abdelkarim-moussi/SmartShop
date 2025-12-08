package com.app.smartshop.infrastructure.httpRequestsFilter;

import com.app.smartshop.application.util.LoginResult;
import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.domain.enums.UserRole;
import com.app.smartshop.domain.entity.User;
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
        String clientId = request.getParameter("id");

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

        if(!isAuthorized(result.getUser(),path,clientId)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request,response);

    }


    private boolean isClientPath(String path, Client authClient, String requestClientId){

        if(path.startsWith("/api/v1/products/all")){
            return true;
        }

        if (authClient == null) {
            return false;
        }

        if(path.startsWith("/api/v1/clients/orders")) {
            return requestClientId != null && requestClientId.equals(authClient.getId());
        }

        else if (path.startsWith("/api/v1/clients/statistics")) {
            return requestClientId != null && requestClientId.equals(authClient.getId());
        }

        return false;
    }

    private boolean isAuthorized(User user,String path,String clientId){

        UserRole role = user.getRole();

        if(role.equals(UserRole.ADMIN)){
            return true;
        }

        if(role.equals(UserRole.CLIENT)){
            return isClientPath(path, user.getClient(), clientId);
        }

        return false;
    }

    private boolean isPublic(String path){
        return path.startsWith("/api/v1/login");
    }

}
