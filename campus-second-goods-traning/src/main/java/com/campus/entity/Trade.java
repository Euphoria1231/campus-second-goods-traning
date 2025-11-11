package com.campus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;
    private String productTitle;
    private Double productPrice;
    private String productImage;
    private Long buyerId;
    private Long sellerId;
    private String status; // PENDING, ACCEPTED, SHIPPED, COMPLETED, CANCELLED
    private Double totalAmount;
    private Integer quantity;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
