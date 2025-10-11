package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.entity.Trade;
import com.campus.entity.TradePageQueryDTO;
import com.campus.result.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface TradeMapper extends BaseMapper<Trade> {
  //  @Select("SELECT * FROM trade WHERE (#{status} IS NULL OR status = #{status})")
   // List<Trade> selectTrades(@Param("status") String status);
  IPage<Trade> pageQuery(IPage<Trade> page, @Param("dto") TradePageQueryDTO tradePageQueryDTO);
  //  @Select("SELECT * FROM trade WHERE id = #{id}")
    Trade selectTradeById(@Param("id") Long id);

   // @Update("UPDATE trade SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateTradeStatus(@Param("id") Long id, @Param("status") String status);
}
