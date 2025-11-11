package com.campus.controller;

import com.campus.entity.Users;
import com.campus.service.EditInfoService;
import com.campus.vo.Result;
import com.campus.vo.UpdateUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
//@CrossOrigin(origins = "http://localhost:5173")
public class EditInfoController {

    @Autowired
    private EditInfoService editInfoService;

    /**
     * 获取用户信息
     */
    @GetMapping("/profile")
    public Result<Users> getUserProfile(@RequestAttribute Integer userId) {
        log.info("获取用户信息请求: userId={}", userId);

        try {
            Users user = editInfoService.getUserInfo(userId);
            log.info("获取用户信息成功: userId={}, username={}", userId, user.getUsername());
            return Result.success("获取用户信息成功", user);
        } catch (RuntimeException e) {
            log.warn("获取用户信息失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/update-profile")
    public Result<Users> updateUserProfile(@RequestBody UpdateUserVo updateUserVo, @RequestAttribute Integer userId) {
        log.info("更新用户信息请求: userId={}, email={}, realName={}",
                userId, updateUserVo.getEmail(), updateUserVo.getRealName());

        try {
            // 设置请求中的用户ID（从token中获取）
            updateUserVo.setUserId(userId);

            Users updatedUser = editInfoService.updateUserInfo(updateUserVo);
            log.info("更新用户信息成功: userId={}, username={}", userId, updatedUser.getUsername());
            return Result.success("更新用户信息成功", updatedUser);
        } catch (RuntimeException e) {
            log.warn("更新用户信息失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}