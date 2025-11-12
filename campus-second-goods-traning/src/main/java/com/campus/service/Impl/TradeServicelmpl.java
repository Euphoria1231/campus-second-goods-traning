package com.campus.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.entity.Trade;
import com.campus.entity.TradeCreateDTO;
import com.campus.entity.TradePageQueryDTO;
import com.campus.mapper.TradeMapper;
import com.campus.utils.PageResult;
import com.campus.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public Trade createTrade(TradeCreateDTO tradeCreateDTO) {
        Trade trade = new Trade();
        System.out.println("生成的自增之前的 ID：" + trade.getId());
        trade.setProductId(tradeCreateDTO.getId());
        trade.setBuyerId(tradeCreateDTO.getBuyer_id());
        trade.setSellerId(tradeCreateDTO.getSeller_id());
        trade.setProductImage(tradeCreateDTO.getImage_url());
        trade.setProductPrice(tradeCreateDTO.getPrice());
        trade.setProductTitle(tradeCreateDTO.getName());
        trade.setShippingAddress(tradeCreateDTO.getTrade_localtion());

        trade.setStatus("PENDING");
        trade.setQuantity(1);
        trade.setTotalAmount(trade.getProductPrice()*trade.getQuantity());
        trade.setCreatedAt(LocalDateTime.now());
        trade.setUpdatedAt(LocalDateTime.now());
        tradeMapper.insertOneTrade(trade);

        return trade;
    }

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
