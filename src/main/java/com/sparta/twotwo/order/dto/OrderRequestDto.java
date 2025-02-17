package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.entity.Order;

public class OrderRequestDto {

    private Long memberId;
    private Long storeId;
    private OrderType orderType;
    private Long price;

    public Order toEntity(){
        return Order.builder()
                .price(price)
                .order_type(orderType)
                .build();
    }

}
