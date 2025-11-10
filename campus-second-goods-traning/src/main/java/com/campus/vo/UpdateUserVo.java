package com.campus.vo;

import lombok.Data;

@Data
public class UpdateUserVo {
    private Integer userId;
    private String email;
    private String phoneNumber;
    private String realName;
}