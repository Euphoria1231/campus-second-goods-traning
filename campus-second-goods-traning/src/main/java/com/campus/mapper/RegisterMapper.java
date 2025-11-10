package com.campus.mapper;

import com.campus.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterMapper {

    /**
     * 插入新用户
     */
    @Insert("INSERT INTO users (username, password, email, phone_number, real_name, school_id, credit_score, created_at, updated_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{phoneNumber}, #{realName}, #{schoolId}, 100, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(Users user);

    /**
     * 根据用户名查询用户（检查用户名是否已存在）
     */
    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    int countByUsername(String username);

    /**
     * 根据邮箱查询用户（检查邮箱是否已存在）
     */
    @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
    int countByEmail(String email);

    /**
     * 根据学号查询用户（检查学号是否已存在）
     */
    @Select("SELECT COUNT(*) FROM users WHERE school_id = #{schoolId}")
    int countBySchoolId(String schoolId);
}