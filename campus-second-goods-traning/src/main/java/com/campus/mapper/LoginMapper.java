package com.campus.mapper;

import com.campus.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT user_id, username, password, email, phone_number, avatar_url, " +
            "real_name, school_id, credit_score, created_at, updated_at " +
            "FROM users WHERE username = #{username} And password = #{password}")
    Users findByUsername(@Param("username") String username, @Param("password") String password);

    //不是，鸽们，EditInfoMapper里面第一个接口不就是根据用户ID查询用户信息吗？
    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @Select("SELECT user_id, username, email, phone_number, avatar_url, " +
            "real_name, school_id, credit_score, created_at, updated_at " +
            "FROM users WHERE user_id = #{userId}")
    Users findById(@Param("userId") Integer userId);
}