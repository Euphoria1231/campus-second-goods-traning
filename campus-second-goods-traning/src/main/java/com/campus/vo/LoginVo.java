package com.campus.vo;
//vo:存放视图类
import lombok.Data;

@Data
public class LoginVo {
    private String username;
    private String password;
}