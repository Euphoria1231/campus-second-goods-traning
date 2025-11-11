package com.campus.service;

import com.campus.vo.ForgotPasswordVo;

public interface ForgotPasswordService {

    /**
     * 验证用户身份
     * @param forgotPasswordVo 验证信息
     * @return 是否验证成功
     */
    boolean verifyIdentity(ForgotPasswordVo forgotPasswordVo);

    /**
     * 重置密码
     * @param forgotPasswordVo 密码信息
     * @return 是否重置成功
     */
    boolean resetPassword(ForgotPasswordVo forgotPasswordVo);
}