package com.campus.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.entity.Trade;
import com.campus.entity.TradePageQueryDTO;
import com.campus.mapper.TradeMapper;
import com.campus.result.PageResult;
import com.campus.service.TradeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServicelmpl implements TradeService {
    @Autowired
    private TradeMapper tradeMapper;

    /*
    @Override
    public List<Trade> getTrades(String status) {

        return tradeMapper.selectTrades(status);
    }*/
    @Override
    public PageResult pageQuery(TradePageQueryDTO tradePageQueryDTO) {
        Page<Trade> page = new Page<>(tradePageQueryDTO.getPage(), tradePageQueryDTO.getPageSize());

        // 添加日志
        System.out.println("执行分页查询，参数: " + tradePageQueryDTO);

        // 确保这里调用的是正确的 mapper 方法
        IPage<Trade> tradePage = tradeMapper.pageQuery(page, tradePageQueryDTO);

        System.out.println("查询结果总数: " + tradePage.getTotal());
        System.out.println("查询结果记录数: " + tradePage.getRecords().size());

        PageResult pageResult = new PageResult();
        pageResult.setTotal(tradePage.getTotal());
        pageResult.setRecords(tradePage.getRecords());
        return pageResult;
    }

    @Override
    public Trade getTradeById(Long id) {
        return tradeMapper.selectTradeById(id);
    }

    @Override
    public Trade updateTradeStatus(Long id, String status) {
        tradeMapper.updateTradeStatus(id, status);
        return tradeMapper.selectTradeById(id);
    }
}
