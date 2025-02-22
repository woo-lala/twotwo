package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderProductDto {
    private UUID orderProductId;
    private UUID order;
    private UUID product;
    private Long quantity;
}
