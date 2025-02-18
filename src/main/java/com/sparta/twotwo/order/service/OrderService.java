package com.sparta.twotwo.order.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.dto.OrderProductRequestDto;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.repository.OrderRepository;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.repository.StoreRepository;
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
    private final StoreRepository storeRepository;

    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto, OrderProductRequestDto orderProductRequestDto) {
        //TODO member가 없을시 예외
        Member findMember = memberRepository.findById(orderRequestDto.getMemberId()).orElseThrow();
        Store findStore = storeRepository.findById(orderRequestDto.getStoreId())
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND));


        Order savedOrder = orderRepository.save(orderRequestDto.toEntity(findMember, findStore));
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
