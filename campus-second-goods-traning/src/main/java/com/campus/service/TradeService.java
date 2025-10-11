package com.campus.service;

import com.campus.entity.Trade;
import com.campus.entity.TradePageQueryDTO;
import java.util.List;
import com.campus.result.PageResult;

public interface TradeService {
    //List<Trade> getTrades(String status);
     PageResult pageQuery(TradePageQueryDTO tradePageQueryDTO);
    Trade getTradeById(Long id);
    Trade updateTradeStatus(Long id, String status);
}
