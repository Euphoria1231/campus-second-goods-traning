package com.campus.controller;

import com.campus.entity.Goods;
import com.campus.service.GoodsService;
import com.campus.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
@CrossOrigin(origins = "*")
@Slf4j
public class GoodsController {
    
    @Autowired
    private GoodsService goodsService;
    
    /**
     * 创建商品
     */
    @PostMapping("/create")
    public Result<Boolean> createGoods(@Valid @RequestBody Goods goods) {
        try {
            log.info("创建商品请求: {}", goods.getName());
            boolean success = goodsService.createGoods(goods);
            if (success) {
                log.info("商品创建成功: {}", goods.getName());
                return Result.success(true, "商品创建成功");
            } else {
                log.warn("商品创建失败: {}", goods.getName());
                return Result.fail("商品创建失败");
            }
        } catch (Exception e) {
            log.error("商品创建异常: {}", goods.getName(), e);
            return Result.fail("商品创建失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改商品
     */
    @PutMapping("/update")
    public Result<Boolean> updateGoods(@Valid @RequestBody Goods goods) {
        try {
            boolean success = goodsService.updateGoods(goods);
            if (success) {
                return Result.success(true, "商品修改成功");
            } else {
                return Result.fail("商品修改失败");
            }
        } catch (Exception e) {
            return Result.fail("商品修改失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取商品
     */
    @GetMapping("/{id}")
    public Result<Goods> getGoodsById(@PathVariable Long id) {
        try {
            Goods goods = goodsService.getGoodsById(id);
            if (goods != null) {
                return Result.success(goods, "获取商品成功");
            } else {
                return Result.fail("商品不存在");
            }
        } catch (Exception e) {
            return Result.fail("获取商品失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有商品
     */
    @GetMapping("/list")
    public Result<List<Goods>> getAllGoods() {
        try {
            List<Goods> goodsList = goodsService.getAllGoods();
            return Result.success(goodsList, "获取商品列表成功");
        } catch (Exception e) {
            return Result.fail("获取商品列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据分类获取商品
     */
    @GetMapping("/category/{category}")
    public Result<List<Goods>> getGoodsByCategory(@PathVariable String category) {
        try {
            List<Goods> goodsList = goodsService.getGoodsByCategory(category);
            return Result.success(goodsList, "获取分类商品成功");
        } catch (Exception e) {
            return Result.fail("获取分类商品失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据卖家ID获取商品
     */
    @GetMapping("/seller/{sellerId}")
    public Result<List<Goods>> getGoodsBySellerId(@PathVariable Long sellerId) {
        try {
            List<Goods> goodsList = goodsService.getGoodsBySellerId(sellerId);
            return Result.success(goodsList, "获取卖家商品成功");
        } catch (Exception e) {
            return Result.fail("获取卖家商品失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteGoods(@PathVariable Long id) {
        try {
            boolean success = goodsService.deleteGoods(id);
            if (success) {
                return Result.success(true, "商品删除成功");
            } else {
                return Result.fail("商品删除失败");
            }
        } catch (Exception e) {
            return Result.fail("商品删除失败: " + e.getMessage());
        }
    }
}
