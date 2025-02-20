package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.entity.OrderProduct;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long memberId;
    private UUID orderId;
    private UUID storeId;
    private OrderType orderType;
    private Long price;
    private List<OrderProduct> orderProduct;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
