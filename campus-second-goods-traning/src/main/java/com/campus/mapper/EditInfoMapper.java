package com.campus.mapper;

import com.campus.entity.Goods;
import com.campus.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EditInfoMapper {

    /**
     * 根据用户ID查询用户信息
     */
    @Select("SELECT user_id, username, email, phone_number, avatar_url, real_name, school_id, credit_score, created_at, updated_at " +
            "FROM users WHERE user_id = #{userId}")
    Users findUserById(@Param("userId") Integer userId);

    /**
     * 更新用户信息
     */
    @Update("UPDATE users SET email = #{email}, phone_number = #{phoneNumber}, real_name = #{realName}, updated_at = NOW() " +
            "WHERE user_id = #{userId}")
    int updateUserInfo(@Param("userId") Integer userId,
                       @Param("email") String email,
                       @Param("phoneNumber") String phoneNumber,
                       @Param("realName") String realName);

    /**
     * 查询用户近期的商品信息
     */
    @Select("select * from goods where seller_id = #{userId}")
    List<Goods> findRecentGoods(@Param("userId") Integer userId);

    /**
     * 检查邮箱是否被其他用户使用
     */
    @Select("SELECT COUNT(*) FROM users WHERE email = #{email} AND user_id != #{userId}")
    int countEmailByOtherUser(@Param("email") String email, @Param("userId") Integer userId);

    /**
     * 检查手机号是否被其他用户使用
     */
    @Select("SELECT COUNT(*) FROM users WHERE phone_number = #{phoneNumber} AND user_id != #{userId}")
    int countPhoneByOtherUser(@Param("phoneNumber") String phoneNumber, @Param("userId") Integer userId);
}