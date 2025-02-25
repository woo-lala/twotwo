package com.sparta.twotwo.order.repository;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.dto.SearchRequestDto;
import com.sparta.twotwo.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderRepositoryCustom {
    Order findByOrderId(UUID orderId);
    Page<Order> searchOrders(SearchRequestDto searchRequestDto, Pageable pageable);
}
