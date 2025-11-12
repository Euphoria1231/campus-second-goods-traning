package com.campus.controller;

import com.campus.dto.UserDTO;
import com.campus.entity.Review;
import com.campus.service.ReviewService;
import com.campus.utils.Result;
import com.campus.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping
    public Result saveReview(@RequestBody Review review){
        UserDTO user = UserHolder.getUser();
        review.setReviewerId(user.getId());
        return reviewService.saveReview(review);
    }


}
