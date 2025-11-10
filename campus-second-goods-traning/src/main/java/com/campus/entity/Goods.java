package com.campus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("goods")
public class Goods {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("name")
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称长度不能超过100个字符")
    private String name;
    
    @TableField("description")
    @NotBlank(message = "商品描述不能为空")
    @Size(max = 1000, message = "商品描述长度不能超过1000个字符")
    private String description;
    
    @TableField("price")
    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    @Digits(integer = 8, fraction = 2, message = "价格格式不正确")
    private BigDecimal price;
    
    @TableField("category")
    @NotBlank(message = "商品分类不能为空")
    @Size(max = 50, message = "商品分类长度不能超过50个字符")
    private String category;
    
    @TableField("condition_status")
    @NotBlank(message = "商品成色不能为空")
    @Size(max = 20, message = "商品成色长度不能超过20个字符")
    private String conditionStatus;
    
    @TableField("image_url")
    private String imageUrl;
    
    @TableField("trade_time")
    private String tradeTime;
    
    @TableField("trade_location")
    private String tradeLocation;
    
    @TableField("contact_phone")
    private String contactPhone;
    
    @TableField("seller_id")
    private Long sellerId;
    
    @TableField("status")
    private String status;
    
    @TableField("create_time")
    private LocalDateTime createTime;
    
    @TableField("update_time")
    private LocalDateTime updateTime;
}
