package com.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.entity.Review;
import com.campus.utils.Result;

import java.util.List;

public interface ReviewService extends IService<Review> {
    //发布评价
    Result<Boolean> saveReview(Review review);
    
    //根据商品ID查询评价列表
    Result<List<Review>> getReviewsByProductId(Long productId);
    
    //根据用户ID查询收到的评价列表
    Result<List<Review>> getReviewsByRevieweeId(Long revieweeId);
    
    //根据用户ID查询发出的评价列表
    Result<List<Review>> getReviewsByReviewerId(Long reviewerId);
    
    //根据交易ID查询评价
    Result<Review> getReviewByOrderId(Long orderId);
}
