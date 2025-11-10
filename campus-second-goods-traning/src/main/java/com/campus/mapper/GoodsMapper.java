package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.entity.Goods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    
    /**
     * 物理删除商品记录
     */
    @Delete("DELETE FROM goods WHERE id = #{id}")
    int physicalDeleteById(Long id);
}
