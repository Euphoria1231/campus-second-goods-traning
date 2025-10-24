package com.campus.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.entity.Goods;
import com.campus.mapper.GoodsMapper;
import com.campus.service.GoodsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    
    @Override
    public boolean createGoods(Goods goods) {
        goods.setCreateTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        goods.setStatus("ACTIVE");
        return save(goods);
    }
    
    @Override
    public boolean updateGoods(Goods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return updateById(goods);
    }
    
    @Override
    public Goods getGoodsById(Long id) {
        return getById(id);
    }
    
    @Override
    public List<Goods> getAllGoods() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "ACTIVE");
        return list(queryWrapper);
    }
    
    @Override
    public List<Goods> getGoodsByCategory(String category) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", category);
        queryWrapper.eq("status", "ACTIVE");
        return list(queryWrapper);
    }
    
    @Override
    public List<Goods> getGoodsBySellerId(Long sellerId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seller_id", sellerId);
        queryWrapper.eq("status", "ACTIVE");
        return list(queryWrapper);
    }
    
    @Override
    public boolean deleteGoods(Long id) {
        // 使用自定义的物理删除方法，确保真正删除记录
        return baseMapper.physicalDeleteById(id) > 0;
    }
}
