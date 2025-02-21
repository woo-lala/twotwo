package com.sparta.twotwo.order.service;

import com.sparta.twotwo.auth.util.SecurityUtil;
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

import com.sparta.twotwo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;


    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto, Member member, UUID storeId) {
        log.info("orderRequestDto: {}", orderRequestDto);
        log.info("member: {}", member);
        log.info("storeId: {}", storeId);


        Store findStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND));
        Order order = orderRequestDto.toOrderEntity(member, findStore);

        //TODO Store not found 에러 추가
        List<OrderProductRequestDto> orderProductRequestDtos = orderRequestDto.getOrderProductRequestDtos();

        Long totalPrice = 0L;
        for(OrderProductRequestDto dto : orderProductRequestDtos) {
            log.info("dto: {}", dto);

            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(()-> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));
            log.info("product: {}", product);
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(dto.getQuantity())
                    .build();

            log.info("@@@@@@@@@@@@orderProduct: {}", orderProduct);
            totalPrice += product.getPrice() * dto.getQuantity();
            //영속성 전이
            order.addOrderProductList(orderProduct);
        }
        
        order.setTotalPrice(totalPrice);

        log.info("order: {}", order);

        Order savedOrder = orderRepository.save(order);

        return savedOrder.toResponseDto();

    }


    public Page<Order> getOrders(int page, int size, String sortBy, boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<String> roles = member.getRoles();

        if(!roles.contains(RolesEnum.MANAGER.getAuthority()) && !roles.contains(RolesEnum.MASTER.getAuthority())){
            //TODO 권한 에러 추가
            throw new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND);
        }

        Page<Order> orders = orderRepository.findAll(pageable);

        List<Order> orderList = orders.getContent();

// 주문 내용 출력
        for (Order order : orderList) {
            System.out.println(order);
        }

        return orders;
    }


    public Order getOrderById(UUID orderId) {

        Order order = orderRepository.findByOrderId(orderId);
        log.info("order {}", order.toString());
        return order;
    }


    public Order updateOrder(UUID orderId, UUID productId, OrderRequestDto orderRequestDto) {

        OrderProduct orderProduct = orderProductRepository.findByOrderIdAndProductId(orderId, productId);
        log.info("orderProduct {}", orderProduct.toString());

//        Product product = productRepository.findById(orderRequestDto.getProductId())
//                .orElseThrow(()->new TwotwoApplicationException(ErrorCode.PRODUCT_NOT_FOUND));
//        orderProduct.changeProduct(product);
//        orderProduct.changeQuantity(orderRequestDto.getQuantity());
//
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));
//
//        order.calculateTotalPrice(orderProduct.getProduct().getPrice(), orderRequestDto.getQuantity());
//
//        orderRepository.save(order);
//        orderProductRepository.save(orderProduct);
//
//        return order;
        return null;
    }

    public void deleteOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));
        order.setDeletedBy(SecurityUtil.getMemberIdFromSecurityContext());
        order.setDeletedAt(LocalDateTime.now());
        orderRepository.save(order);
        order.getOrderProducts().stream()
                        .map(orderProduct ->{
                            orderProduct.setDeletedBy(SecurityUtil.getMemberIdFromSecurityContext());
                            orderProduct.setDeletedAt(LocalDateTime.now());
                            return orderProduct;
                        })
                .forEach(orderProduct -> orderProductRepository.save(orderProduct));


        orderRepository.deleteById(orderId);

    }

}
