package com.app.smartshop.infrastructure.config;

import com.app.smartshop.infrastructure.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final AuthorizationFilter authorizationFilter;

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authFilter(){
        FilterRegistrationBean<AuthorizationFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(authorizationFilter);
        bean.addUrlPatterns("/api/v1/*");
        bean.setOrder(1);
        return bean;
    }
}
