package com.sparta.twotwo.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    private List<OrderProductDto> orderProduct;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
