package com.campus.filter;

import com.campus.util.CurrentHolder;
import com.campus.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    //注意：tokenfilter不会拦截任何请求，但要通过token解析获取到用户ID
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURL = request.getRequestURL().toString();
        log.info("请求URL: {}", requestURL);

        // 即使是登录/注册等公开接口，也尝试解析 token
        String token = request.getHeader("token");

        Integer userId = null;
        String username = null;

        if (StringUtils.hasText(token)) {
            try {
                // 清理 Bearer 前缀
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                // 尝试解析 token
                Claims claims = JwtUtils.parseJWT(token);
                userId = claims.get("userId", Integer.class);
                username = claims.get("username", String.class);

                log.info("Token 解析成功，用户ID: {}, 用户名: {}", userId, username);

                // 设置到 ThreadLocal 和 Request Attribute
                CurrentHolder.setCurrentId(userId);
                request.setAttribute("userId", userId);
                request.setAttribute("username", username);

            } catch (Exception e) {
                log.warn("Token 解析失败，但忽略: {}", e.getMessage());
                // 不中断流程，不清除已设属性，继续放行
            }
        } else {
            log.debug("请求中未携带 token");
        }

        // ========== 关键：无论是否有 token，一律放行！==========
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 清理 ThreadLocal，防止内存泄漏
            CurrentHolder.remove();
            log.debug("已清理 ThreadLocal 中的用户上下文");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("TokenFilter 初始化完成");
    }

    @Override
    public void destroy() {
        log.info("TokenFilter 销毁");
        CurrentHolder.remove();
    }
}