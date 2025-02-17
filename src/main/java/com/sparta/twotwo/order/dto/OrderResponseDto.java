package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long memberId;
    private UUID order_id;
    private Long storeId;
    private OrderType order_type;
    private Long price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
