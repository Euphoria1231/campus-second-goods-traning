package com.campus.vo;

import lombok.Data;

@Data
public class RegisterVo {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String phoneNumber;
    private String realName;
    private String schoolId;
}