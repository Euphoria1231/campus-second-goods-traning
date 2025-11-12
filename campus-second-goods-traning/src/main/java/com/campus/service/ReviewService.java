package com.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.entity.Review;
import com.campus.utils.Result;

public interface ReviewService extends IService<Review> {
    //发布评价
    Result saveReview(Review review);
}
