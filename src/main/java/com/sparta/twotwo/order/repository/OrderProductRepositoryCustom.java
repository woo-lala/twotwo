package com.sparta.twotwo.order.repository;

import com.sparta.twotwo.order.entity.OrderProduct;

import java.util.UUID;

public interface OrderProductRepositoryCustom {
    OrderProduct findByOrderIdAndProductId(UUID orderId, UUID productId);
}
