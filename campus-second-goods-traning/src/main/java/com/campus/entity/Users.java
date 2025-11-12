package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    /*
    * user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID，主键',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，唯一',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    phone_number VARCHAR(20) COMMENT '手机号',
    avatar_url VARCHAR(255) COMMENT '头像链接',
    real_name VARCHAR(50) COMMENT '真实姓名',
    school_id VARCHAR(20) COMMENT '学号',
    credit_score INT DEFAULT 100 COMMENT '信用分，默认100',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
    *
    */
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    private String realName;
    private String schoolId;
    private Integer creditScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String token; //令牌
}
