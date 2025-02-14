package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private UUID order_id;
    private OrderType order_type;
    private Long price;

}
