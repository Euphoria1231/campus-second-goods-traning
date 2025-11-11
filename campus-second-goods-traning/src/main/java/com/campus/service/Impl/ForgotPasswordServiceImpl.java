package com.campus.service.Impl;

import com.campus.entity.Users;
import com.campus.mapper.ForgotPasswordMapper;
import com.campus.service.ForgotPasswordService;
import com.campus.vo.ForgotPasswordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private ForgotPasswordMapper forgotPasswordMapper;

    // 信用分封禁阈值
    private static final Integer BANNED_CREDIT_SCORE = 60;

    /**
     * 验证身份信息
     */
    @Override
    public boolean verifyIdentity(ForgotPasswordVo forgotPasswordVo) {
        // 1. 参数校验
        validateVerifyInfo(forgotPasswordVo);

        // 2. 检查用户是否存在且未被封禁
        Users user = forgotPasswordMapper.findUserForVerification(forgotPasswordVo.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 3. 检查账号是否被封禁
        if (user.getCreditScore() < BANNED_CREDIT_SCORE) {
            throw new RuntimeException("账号已被封禁，无法重置密码");
        }

        // 4. 验证身份信息是否匹配
        int count = forgotPasswordMapper.verifyUserIdentity(
                forgotPasswordVo.getUsername(),
                forgotPasswordVo.getPhoneNumber(),
                forgotPasswordVo.getRealName(),
                forgotPasswordVo.getSchoolId()
        );

        if (count == 0) {
            throw new RuntimeException("身份信息验证失败，请检查输入的信息");
        }

        return true;
    }

    /**
     * 重置密码
     */
    @Override
    public boolean resetPassword(ForgotPasswordVo forgotPasswordVo) {
        // 1. 参数校验
        validateResetInfo(forgotPasswordVo);

        // 2. 获取用户ID
        Users user = forgotPasswordMapper.findUserForVerification(forgotPasswordVo.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 3. 更新密码
        int result = forgotPasswordMapper.updatePassword(user.getUserId(), forgotPasswordVo.getNewPassword());
        if (result <= 0) {
            throw new RuntimeException("密码重置失败，请稍后重试");
        }

        return true;
    }

    /**
     * 验证身份信息参数
     */
    private void validateVerifyInfo(ForgotPasswordVo forgotPasswordVo) {
        if (!StringUtils.hasText(forgotPasswordVo.getUsername())) {
            throw new RuntimeException("用户名不能为空");
        }
        if (!StringUtils.hasText(forgotPasswordVo.getPhoneNumber())) {
            throw new RuntimeException("手机号不能为空");
        }
        if (!StringUtils.hasText(forgotPasswordVo.getRealName())) {
            throw new RuntimeException("真实姓名不能为空");
        }
        if (!StringUtils.hasText(forgotPasswordVo.getSchoolId())) {
            throw new RuntimeException("学号不能为空");
        }

        // 手机号格式验证
        if (!forgotPasswordVo.getPhoneNumber().matches("^1[3-9]\\d{9}$")) {
            throw new RuntimeException("请输入有效的手机号");
        }
    }

    /**
     * 验证重置密码参数
     */
    private void validateResetInfo(ForgotPasswordVo forgotPasswordVo) {
        if (!StringUtils.hasText(forgotPasswordVo.getUsername())) {
            throw new RuntimeException("用户名不能为空");
        }
        if (!StringUtils.hasText(forgotPasswordVo.getNewPassword()) || forgotPasswordVo.getNewPassword().length() < 6) {
            throw new RuntimeException("新密码至少6个字符");
        }
    }
}