package com.campus.intercepter;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * WebSocket握手拦截器，用于身份验证
 */
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 从请求中获取用户信息
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            
            // 从请求参数或header中获取userId
            String userId = httpServletRequest.getParameter("userId");
            if (userId == null || userId.isEmpty()) {
                userId = httpServletRequest.getHeader("userId");
            }
            
            // 如果存在userId，将其存储到attributes中
            if (userId != null && !userId.isEmpty()) {
                attributes.put("userId", userId);
                attributes.put("principal", new StompPrincipal(userId));
                return true;
            }
            
            // 可以根据实际需求决定是否允许匿名连接
            // 如果不允许，返回false；如果允许，返回true
            return true;
        }
        
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 握手完成后的处理，可以留空或添加日志
    }

    /**
     * STOMP协议的Principal实现
     */
    public static class StompPrincipal implements Principal {
        private final String name;

        public StompPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
