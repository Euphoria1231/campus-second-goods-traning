package com.campus.controller;

import com.campus.entity.Trade;
import com.campus.entity.TradePageQueryDTO;
import com.campus.result.PageResult;
import com.campus.result.Result;
import com.campus.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    // 获取交易列表
    /*
    @GetMapping
    public Result<List<Trade>> getTrades(@RequestParam(required = false) String status) {
        System.out.println(status);
        return Result.success(tradeService.getTrades(status));
    }*/

    @GetMapping
    public Result<PageResult> pageQuery(TradePageQueryDTO tradePageQueryDTO){
        System.out.println(tradePageQueryDTO);
        PageResult pageResult =tradeService.pageQuery(tradePageQueryDTO);
        return Result.success(pageResult);
    }

    // 获取单个交易详情
    @GetMapping("/{id}")
    public Result<Trade> getTradeById(@PathVariable Long id) {
        System.out.println("查看交易");
        System.out.println(id);
        return Result.success(tradeService.getTradeById(id));
    }

    // 更新交易状态
    @PatchMapping("/{id}")
    public Result<Trade> updateTradeStatus(@PathVariable Long id, @RequestBody Trade trade) {
        return Result.success(tradeService.updateTradeStatus(id, trade.getStatus()));
    }



}
