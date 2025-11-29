package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeCreateDTO {
    private Long id;
    private String name;
    private double price;
    private String image_url;
    private String trade_localtion;
    private Long seller_id;
}
