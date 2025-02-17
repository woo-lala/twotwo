package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.members.entity.Member;
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
    private Long createdBy;
    private Long updatedBy;
    private Long deletedBy;

    //TODO StoreId 받기
    public Order toEntity(Member member) {
        return Order.builder()
                .price(price)
                .order_type(orderType)
                .member(member)
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
