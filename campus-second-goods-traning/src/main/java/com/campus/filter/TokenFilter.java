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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        // ========== 原始过滤逻辑已注释，直接放行所有请求 ==========
        /*
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            // 1. 获取请求url
            String requestURL = request.getRequestURL().toString();
            log.info("请求URL: {}", requestURL);

            // 2. 判断请求url中是否包含login、register、forgot-password，如果包含，放行
            if (requestURL.contains("/api/user/login") || requestURL.contains("/api/user/register") || requestURL.contains("/api/user/verify-identity") || requestURL.contains("/api/user/reset-password")) {
                log.info("请求可以直接放行");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 3. 获取请求头中的令牌（token）
            String token = request.getHeader("token");
            log.info("获取到的Token: {}", token);

            // 4. 判断令牌是否存在，如果不存在，响应 401
            if (!StringUtils.hasText(token)) {
                log.warn("令牌不存在，返回401未授权");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"未授权访问，请先登录\"}");
                return;
            }

            // 清理Bearer前缀（如果存在）
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 5. 解析token
            Claims claims = JwtUtils.parseJWT(token);
            Integer userId = claims.get("userId", Integer.class);
            String username = claims.get("username", String.class);

            log.info("令牌解析成功，用户信息: userId={}, username={}", userId, username);

            // 将用户ID存入ThreadLocal
            CurrentHolder.setCurrentId(userId);
            log.info("已将用户ID存入ThreadLocal: userId={}", userId);

            // 将用户信息存入请求属性，供后续使用
            request.setAttribute("userId", userId);
            request.setAttribute("username", username);

            // 6. 放行
            log.info("令牌校验通过，放行请求");
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            log.error("令牌解析失败: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"令牌无效或已过期\"}");
        } finally {
            // 无论请求成功还是失败，都要清理ThreadLocal，防止内存泄漏
            CurrentHolder.remove();
            log.info("已清理ThreadLocal中的用户ID");
        }
        */

        // 直接放行所有请求
        log.info("TokenFilter: 已禁用过滤，直接放行所有请求");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TokenFilter初始化完成");
    }

    @Override
    public void destroy() {
        log.info("TokenFilter销毁");
        // 确保销毁时清理ThreadLocal
        CurrentHolder.remove();
    }
}
