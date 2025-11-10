package com.campus.service.Impl;

import com.campus.entity.Users;
import com.campus.mapper.LoginMapper;
import com.campus.service.LoginService;
import com.campus.util.JwtUtils;
import com.campus.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    // 信用分封禁阈值
    private static final Integer BANNED_CREDIT_SCORE = 60;

    @Override
    public Users login(LoginVo loginVo) {
        // 参数校验 (已在Controller进行基本检查，此处可根据需要添加)
        // 根据用户名查询用户
        Users user = loginMapper.findByUsername(loginVo.getUsername(), loginVo.getPassword());
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 检查账号是否被封禁（信用分过低）
        if (user.getCreditScore() < BANNED_CREDIT_SCORE) {
            throw new RuntimeException("账号已被封禁，信用分过低");
        }

        // 验证密码
        if (!user.getPassword().equals(loginVo.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 登录成功，生成JWT令牌
        String token = generateUserToken(user);

//        log.info("用户登录成功，生成JWT令牌: {}", token);

        // 设置令牌到用户对象
        user.setToken(token);

        // 删除密码信息，保障用户信息安全
        user.setPassword(null);

        // 登录成功，返回用户信息
        return user;
    }

    /**
     * 生成用户JWT令牌
     * @param user 用户信息
     * @return JWT令牌
     */
    private String generateUserToken(Users user) {
        Map<String, Object> claims = new HashMap<>();

        // 在令牌中存储用户关键信息
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("realName", user.getRealName());
        claims.put("schoolId", user.getSchoolId());
        claims.put("creditScore", user.getCreditScore());

        // 生成JWT令牌
        return JwtUtils.generateJwt(claims);
    }
}