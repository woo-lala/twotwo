package com.sparta.twotwo.order.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public OrderResponseDto saveOrder(OrderRequestDto requestDto) {
        //TODO member가 없을시 예외
        // ceratedBy
        Member findMember = memberRepository.findById(requestDto.getMemberId()).orElseThrow();

        Order savedOrder = orderRepository.save(requestDto.toEntity(findMember));
        return savedOrder.toResponseDto();
    }


    public Page<OrderResponseDto> getOrders(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        //TODO UserRole 검사

        Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(Order::toResponseDto);
    }


    public OrderResponseDto getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).map(Order::toResponseDto)
                .orElseThrow(()->new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));
    }
}
