package com.campus.controller;

import com.campus.entity.Users;
import com.campus.service.RegisterService;
import com.campus.vo.RegisterVo;
import com.campus.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
//@CrossOrigin(origins = "http://localhost:5173") // 添加CORS支持
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public Result<Users> register(@RequestBody RegisterVo registerVo) {
        log.info("用户注册请求: username={}, email={}", registerVo.getUsername(), registerVo.getEmail());

        try {
            Users user = registerService.register(registerVo);
            log.info("用户注册成功: userId={}, username={}", user.getUserId(), user.getUsername());
            return Result.success("注册成功", user);
        } catch (RuntimeException e) {
            log.warn("用户注册失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}