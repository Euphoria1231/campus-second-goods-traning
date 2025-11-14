package com.campus.vo;

import com.campus.entity.Goods;
import lombok.Data;

/**
 * 商品详情VO，包含卖家信息
 */
@Data
public class GoodsDetailVo {
    // 商品信息
    private Goods goods;
    
    // 卖家信息
    private SellerInfo seller;
    
    @Data
    public static class SellerInfo {
        private Long sellerId;
        private String username;
        private String avatarUrl;
        private Integer creditScore;
    }
}



