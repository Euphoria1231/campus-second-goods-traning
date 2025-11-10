package com.campus.vo;

import lombok.Data;

@Data
public class ForgotPasswordVo {
    // 验证身份阶段
    private String username;
    private String phoneNumber;
    private String realName;
    private String schoolId;

    // 设置新密码阶段
    private String newPassword;
    private String confirmPassword;
}