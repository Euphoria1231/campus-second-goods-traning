package com.campus.controller;

import com.campus.entity.Users;
import com.campus.service.LoginService;
import com.campus.vo.LoginVo;
import com.campus.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

//请求路径: /api/user/login
//请求方式: POST
//Content-Type: application/json
//请求参数：{
//  "username": "string, 必填, 用户名",
//  "password": "string, 必填, 密码"
//}
@RestController
@RequestMapping("/api/user")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<Users> login(@RequestBody LoginVo loginVo) {
        log.info("用户登录请求: username={}", loginVo.getUsername());

        // 基本的参数非空检查
        if (!StringUtils.hasText(loginVo.getUsername())) {
            return Result.error("用户名不能为空");
        }
        if (!StringUtils.hasText(loginVo.getPassword())) {
            return Result.error("密码不能为空");
        }

        try {
            Users user = loginService.login(loginVo);
            return Result.success("登录成功", user);
        } catch (RuntimeException e) {
            log.warn("登录失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}