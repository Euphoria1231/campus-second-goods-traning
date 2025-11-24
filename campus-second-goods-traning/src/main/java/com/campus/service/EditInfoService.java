package com.campus.service;

import com.campus.entity.Goods;
import com.campus.entity.Users;
import com.campus.vo.UpdateUserVo;
import com.github.pagehelper.PageInfo;

public interface EditInfoService {

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    Users getUserInfo(Integer userId);


    /**
     * 查询近期商品
     * @param userId 用户ID
     * @return 近期商品
     */
    PageInfo<Goods> findRecentGoodsByPage(Integer userId, int pageNum, int pageSize);

    /**
     * 更新用户信息
     * @param updateUserVo 更新信息
     * @return 更新后的用户信息
     */
    Users updateUserInfo(UpdateUserVo updateUserVo);
}