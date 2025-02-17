package com.sparta.twotwo.order.service;

import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponseDto saveOrder(OrderRequestDto requestDto) {
        Order savedOrder = orderRepository.save(requestDto.toEntity());
        return savedOrder.toResponseDto();
    }


    public Page<OrderResponseDto> getOrders(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // User 권한이 매니저 이상
        Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(Order::toResponseDto);
    }








}
