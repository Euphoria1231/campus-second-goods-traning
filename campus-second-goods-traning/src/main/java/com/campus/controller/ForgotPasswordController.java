package com.campus.controller;

import com.campus.service.ForgotPasswordService;
import com.campus.vo.ForgotPasswordVo;
import com.campus.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
//@CrossOrigin(origins = "http://localhost:5173")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    /**
     * 验证身份方法
     */
    @PostMapping("/verify-identity")
    public Result<String> verifyIdentity(@RequestBody ForgotPasswordVo forgotPasswordVo) {
        log.info("身份验证请求: username={}", forgotPasswordVo.getUsername());

        try {
            boolean success = forgotPasswordService.verifyIdentity(forgotPasswordVo);
            if (success) {
                log.info("身份验证成功: username={}", forgotPasswordVo.getUsername());
                return Result.success("身份验证成功", null);
            } else {
                return Result.error("身份验证失败");
            }
        } catch (RuntimeException e) {
            log.warn("身份验证失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 设置新密码方法
     */
    @PostMapping("/reset-password")
    public Result<String> resetPassword(@RequestBody ForgotPasswordVo forgotPasswordVo) {
        log.info("重置密码请求: username={}", forgotPasswordVo.getUsername());
        try {
            boolean success = forgotPasswordService.resetPassword(forgotPasswordVo);
            if (success) {
                log.info("密码重置成功: username={}", forgotPasswordVo.getUsername());
                return Result.success("密码重置成功", null);
            } else {
                return Result.error("密码重置失败");
            }
        } catch (RuntimeException e) {
            log.warn("密码重置失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}