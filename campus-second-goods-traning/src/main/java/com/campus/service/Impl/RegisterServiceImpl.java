package com.campus.service.Impl;

import com.campus.entity.Users;
import com.campus.mapper.RegisterMapper;
import com.campus.service.RegisterService;
import com.campus.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    // 初始信用分
    private static final Integer INITIAL_CREDIT_SCORE = 100;

    @Override
    public Users register(RegisterVo registerVo) {
        // 1. 参数校验
        validateRegisterInfo(registerVo);

        // 2. 检查用户名是否已存在
        if (registerMapper.countByUsername(registerVo.getUsername()) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 3. 检查邮箱是否已存在
        if (registerMapper.countByEmail(registerVo.getEmail()) > 0) {
            throw new RuntimeException("邮箱已被注册");
        }

        // 4. 检查学号是否已存在
        if (registerMapper.countBySchoolId(registerVo.getSchoolId()) > 0) {
            throw new RuntimeException("学号已被注册");
        }

        // 5. 创建用户对象
        Users user = new Users();
        user.setUsername(registerVo.getUsername());
        user.setPassword(registerVo.getPassword()); // 实际生产环境需要加密
        user.setEmail(registerVo.getEmail());
        user.setPhoneNumber(registerVo.getPhoneNumber());
        user.setRealName(registerVo.getRealName());
        user.setSchoolId(registerVo.getSchoolId());
        user.setCreditScore(INITIAL_CREDIT_SCORE);

        // 6. 插入用户数据
        int result = registerMapper.insertUser(user);
        if (result <= 0) {
            throw new RuntimeException("注册失败，请稍后重试");
        }

        // 7. 返回用户信息（清除密码）
        user.setPassword(null);
        return user;
    }

    /**
     * 验证注册信息
     */
    private void validateRegisterInfo(RegisterVo registerVo) {
        if (!StringUtils.hasText(registerVo.getUsername()) || registerVo.getUsername().length() < 3) {
            throw new RuntimeException("用户名至少3个字符");
        }

        if (!StringUtils.hasText(registerVo.getPassword()) || registerVo.getPassword().length() < 6) {
            throw new RuntimeException("密码至少6个字符");
        }

        if (!registerVo.getPassword().equals(registerVo.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        if (!StringUtils.hasText(registerVo.getEmail()) || !isValidEmail(registerVo.getEmail())) {
            throw new RuntimeException("请输入有效的邮箱地址");
        }

        if (!StringUtils.hasText(registerVo.getPhoneNumber()) || !isValidPhone(registerVo.getPhoneNumber())) {
            throw new RuntimeException("请输入有效的手机号");
        }

        if (!StringUtils.hasText(registerVo.getRealName()) || registerVo.getRealName().length() < 2) {
            throw new RuntimeException("真实姓名至少2个字符");
        }

        if (!StringUtils.hasText(registerVo.getSchoolId())) {
            throw new RuntimeException("学号不能为空");
        }
    }

    /**
     * 简单的邮箱格式验证
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * 手机号格式验证
     */
    private boolean isValidPhone(String phone) {
        return phone.matches("^1[3-9]\\d{9}$");
    }
}