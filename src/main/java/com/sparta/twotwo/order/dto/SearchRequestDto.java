package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class SearchRequestDto {

    private String orderId;
    private String storeId;
    private OrderType orderType;
    private Long memberId;

    @Override
    public String toString() {
        return "SearchRequestDto{" +
                "orderId=" + orderId +
                ", storeId=" + storeId +
                ", orderType=" + orderType +
                ", memberId=" + memberId +
                '}';
    }
}
