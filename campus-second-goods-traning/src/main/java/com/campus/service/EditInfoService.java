package com.campus.service;

import com.campus.entity.Users;
import com.campus.vo.UpdateUserVo;

public interface EditInfoService {

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    Users getUserInfo(Integer userId);

    /**
     * 更新用户信息
     * @param updateUserVo 更新信息
     * @return 更新后的用户信息
     */
    Users updateUserInfo(UpdateUserVo updateUserVo);
}