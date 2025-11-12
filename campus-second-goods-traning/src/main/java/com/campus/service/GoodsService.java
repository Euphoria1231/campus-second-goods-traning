package com.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.entity.Goods;

import java.util.List;

public interface GoodsService extends IService<Goods> {
    
    /**
     * 创建商品
     */
    boolean createGoods(Goods goods);
    
    /**
     * 修改商品
     */
    boolean updateGoods(Goods goods);
    
    /**
     * 根据ID获取商品
     */
    Goods getGoodsById(Long id);
    
    /**
     * 获取所有商品
     */
    List<Goods> getAllGoods();
    
    /**
     * 根据分类获取商品
     */
    List<Goods> getGoodsByCategory(String category);
    
    /**
     * 根据卖家ID获取商品
     */
    List<Goods> getGoodsBySellerId(Long sellerId);
    
    /**
     * 删除商品
     */
    boolean deleteGoods(Long id);
}
