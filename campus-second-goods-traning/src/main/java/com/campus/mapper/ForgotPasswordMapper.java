package com.campus.mapper;

import com.campus.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ForgotPasswordMapper {

    /**
     * 根据用户名查询用户信息（用于身份验证）
     */
    @Select("SELECT user_id, username, phone_number, real_name, school_id, credit_score " +
            "FROM users WHERE username = #{username}")
    Users findUserForVerification(@Param("username") String username);

    /**
     * 更新用户密码
     */
    @Update("UPDATE users SET password = #{password}, updated_at = NOW() WHERE user_id = #{userId}")
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);

    /**
     * 验证用户身份信息是否匹配
     */
    @Select("SELECT COUNT(*) FROM users WHERE username = #{username} AND phone_number = #{phoneNumber} " +
            "AND real_name = #{realName} AND school_id = #{schoolId}")
    int verifyUserIdentity(@Param("username") String username,
                           @Param("phoneNumber") String phoneNumber,
                           @Param("realName") String realName,
                           @Param("schoolId") String schoolId);
}