package com.sparta.twotwo.order.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.entity.RolesEnum;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.dto.OrderProductRequestDto;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.entity.OrderProduct;
import com.sparta.twotwo.order.repository.OrderProductRepository;
import com.sparta.twotwo.order.repository.OrderRepository;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.product.repository.ProductRepository;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;


    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto, Member member, UUID storeId) {

        Store findStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND));
        Order order = orderRequestDto.toOrderEntity(member, findStore);
        //TODO Store not found 에러 추가
        Product product = productRepository.findById(orderRequestDto.getProductId())
                .orElseThrow(()-> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(orderRequestDto.getQuantity())
                .build();

        Order savedOrder = orderRepository.save(order);
        orderProductRepository.save(orderProduct);

        return savedOrder.toResponseDto();
    }


    public Page<OrderResponseDto> getOrders(int page, int size, String sortBy, boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<String> roles = member.getRoles();
        if(!roles.contains(RolesEnum.MANAGER) || !roles.contains(RolesEnum.MASTER)){
            //TODO 권한 에러 추가
            throw new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND);
        }



        Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(Order::toResponseDto);
    }


    public OrderResponseDto getOrderById(UUID orderId) {


        return orderRepository.findById(orderId).map(Order::toResponseDto)
                .orElseThrow(()->new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));
    }


    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));

        order.changeOrderTYpe(orderRequestDto.getOrderType());

        OrderProduct orderProduct = orderProductRepository.findByOrder_OrderIdAndProductId(orderId, orderRequestDto.getProductId());
        orderProduct.changeQuantity(orderRequestDto.getQuantity());

        return order.toResponseDto();
    }

}
