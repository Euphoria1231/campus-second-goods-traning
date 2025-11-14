package com.campus.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.dto.UserDTO;
import com.campus.entity.Review;
import com.campus.mapper.ReviewMapper;
import com.campus.service.ReviewService;
import com.campus.utils.Result;
import com.campus.utils.UserHolder;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    @Override
    public Result saveReview(Review review) {
        //1.获取当前登陆的用户
        UserDTO user = UserHolder.getUser();
        review.setReviewerId(user.getId());
        //2.保存评论信息到数据库
        boolean issuccess = save(review);
        //3.判断保存是否成功并返回
        if(!issuccess){
            return Result.fail(500,"保存失败，请重试");
        }
        return Result.success(true);
    }
}
