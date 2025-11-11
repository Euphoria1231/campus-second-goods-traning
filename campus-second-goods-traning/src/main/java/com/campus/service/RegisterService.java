package com.campus.service;

import com.campus.entity.Users;
import com.campus.vo.RegisterVo;

public interface RegisterService {

    /**
     * 用户注册
     * @param registerVo 注册信息
     * @return 注册成功的用户信息
     */
    Users register(RegisterVo registerVo);
}