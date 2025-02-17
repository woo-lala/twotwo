package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "memberId=" + memberId +
                ", storeId=" + storeId +
                ", orderType=" + orderType +
                ", price=" + price +
                '}';
    }
}
