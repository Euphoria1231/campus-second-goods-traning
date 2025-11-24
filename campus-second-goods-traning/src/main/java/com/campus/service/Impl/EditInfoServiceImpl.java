package com.campus.service.Impl;

import com.campus.entity.Goods;
import com.campus.entity.Users;
import com.campus.mapper.EditInfoMapper;
import com.campus.service.EditInfoService;
import com.campus.vo.UpdateUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EditInfoServiceImpl implements EditInfoService {

    @Autowired
    private EditInfoMapper editInfoMapper;

    @Override
    public Users getUserInfo(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new RuntimeException("用户ID不能为空");
        }

        Users user = editInfoMapper.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 清除密码信息
        user.setPassword(null);
        return user;
    }

    //近期商品
    @Override
    public PageInfo<Goods> findRecentGoodsByPage(Integer userId, int pageNum, int pageSize) {
        // 关键：在查询前调用 PageHelper.startPage
        PageHelper.startPage(pageNum, pageSize);

        // 调用查询方法
        List<Goods> goodsList = editInfoMapper.findRecentGoods(userId);

        // 使用 PageInfo 封装结果（包含 total、pages、list 等）
        return new PageInfo<>(goodsList);
    }

    //更新信息
    @Override
    public Users updateUserInfo(UpdateUserVo updateUserVo) {
        // 1. 参数校验
        validateUpdateInfo(updateUserVo);

        // 2. 检查用户是否存在
        Users existingUser = editInfoMapper.findUserById(updateUserVo.getUserId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 3. 检查邮箱是否被其他用户使用
        if (editInfoMapper.countEmailByOtherUser(updateUserVo.getEmail(), updateUserVo.getUserId()) > 0) {
            throw new RuntimeException("邮箱已被其他用户使用");
        }

        // 4. 检查手机号是否被其他用户使用
        if (editInfoMapper.countPhoneByOtherUser(updateUserVo.getPhoneNumber(), updateUserVo.getUserId()) > 0) {
            throw new RuntimeException("手机号已被其他用户使用");
        }

        // 5. 更新用户信息
        int result = editInfoMapper.updateUserInfo(
                updateUserVo.getUserId(),
                updateUserVo.getEmail(),
                updateUserVo.getPhoneNumber(),
                updateUserVo.getRealName()
        );

        if (result <= 0) {
            throw new RuntimeException("更新用户信息失败");
        }

        // 6. 返回更新后的用户信息
        return getUserInfo(updateUserVo.getUserId());
    }

    /**
     * 验证更新信息
     */
    private void validateUpdateInfo(UpdateUserVo updateUserVo) {
        if (updateUserVo.getUserId() == null || updateUserVo.getUserId() <= 0) {
            throw new RuntimeException("用户ID不能为空");
        }

        if (!StringUtils.hasText(updateUserVo.getEmail()) || !isValidEmail(updateUserVo.getEmail())) {
            throw new RuntimeException("请输入有效的邮箱地址");
        }

        if (!StringUtils.hasText(updateUserVo.getPhoneNumber()) || !isValidPhone(updateUserVo.getPhoneNumber())) {
            throw new RuntimeException("请输入有效的手机号");
        }

        if (!StringUtils.hasText(updateUserVo.getRealName()) || updateUserVo.getRealName().length() < 2) {
            throw new RuntimeException("真实姓名至少2个字符");
        }
    }

    /**
     * 邮箱格式验证
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