package com.sparta.twotwo.order.repository;

import com.sparta.twotwo.order.entity.Order;

import java.util.UUID;

public interface OrderRepositoryCustom {
    Order findByOrderId(UUID orderId);
}
