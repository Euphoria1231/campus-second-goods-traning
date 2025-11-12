package com.campus.service;

import com.campus.entity.Users;
import com.campus.vo.LoginVo;

public interface LoginService {

    /**
     * 用户登录
     * @param loginVo 登录信息
     * @return 用户信息
     */
    Users login(LoginVo loginVo);
}