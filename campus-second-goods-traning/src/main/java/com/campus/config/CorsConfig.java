package com.campus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

       // 允许前端地址（明确指定，不能用通配符*）
        config.addAllowedOriginPattern("http://localhost:5500");
        config.addAllowedOriginPattern("http://127.0.0.1:5500");
        
        // 允许所有HTTP方法，包括OPTIONS
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        
        // 允许所有请求头
        config.addAllowedHeader("*");
        
        // 允许暴露的响应头
        config.addExposedHeader("token");
        config.addExposedHeader("Authorization");
        
        // 允许携带认证信息（cookies等）
        config.setAllowCredentials(true);
        
        // 设置预检请求的缓存时间（秒）
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有路径应用CORS配置
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
