package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.entity.Trade;
import com.campus.entity.TradePageQueryDTO;
import org.apache.ibatis.annotations.*;


@Mapper
public interface TradeMapper extends BaseMapper<Trade> {
  //  @Select("SELECT * FROM trade WHERE (#{status} IS NULL OR status = #{status})")
   // List<Trade> selectTrades(@Param("status") String status);

    void insertOneTrade(Trade trade);

    // 在 TradeMapper 接口中修改方法签名
    IPage<Trade> pageQuery(@Param("page") Page<Trade> page,
                           @Param("dto") TradePageQueryDTO tradePageQueryDTO,
                           @Param("currentUserId") Long currentUserId);
  //  @Select("SELECT * FROM trade WHERE id = #{id}")
    Trade selectTradeById(@Param("id") Long id);

   // @Update("UPDATE trade SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateTradeStatus(@Param("id") Long id, @Param("status") String status);
}
