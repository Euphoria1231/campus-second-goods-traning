package com.campus.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String nickName;
    private String icon;

    public String getUsername() {
        return nickName;
    }
}
