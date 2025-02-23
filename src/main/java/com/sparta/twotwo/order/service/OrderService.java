package com.sparta.twotwo.order.service;

import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.dto.OrderProductRequestDto;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.dto.SearchRequestDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    private final MemberRepository memberRepository;


    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto, UUID storeId) {
        log.info("orderRequestDto: {}", orderRequestDto);
        log.info("storeId: {}", storeId);

        Long memberId = SecurityUtil.getMemberIdFromSecurityContext();
        Member member = memberRepository.findById(memberId).orElseThrow();

        Store findStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND));
        Order order = orderRequestDto.toOrderEntity(member, findStore);

        List<OrderProductRequestDto> orderProductRequestDtos = orderRequestDto.getOrderProductRequestDtos();

        Long totalPrice = 0L;
        for(OrderProductRequestDto dto : orderProductRequestDtos) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(()-> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));

            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(dto.getQuantity())
                    .build();

            totalPrice += product.getPrice() * dto.getQuantity();
            //영속성 전이
            order.addOrderProductList(orderProduct);
        }
        
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return savedOrder.toResponseDto();

    }


    public Page<Order> getOrders(int page, int size, String sortBy, boolean isAsc, SearchRequestDto searchRequestDto) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

//        Page<Order> orders = orderRepository.findAll(pageable);
        Page<Order> orders = orderRepository.searchOrders(searchRequestDto, pageable);

        List<Order> orderList = orders.getContent();

        return orders;
    }


    public Order getOrderById(UUID orderId) {

        Order order = orderRepository.findByOrderId(orderId);
        log.info("order {}", order.toString());
        return order;
    }


    public Order updateOrder(UUID orderId, UUID productId, OrderProductRequestDto orderProductRequestDto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new TwotwoApplicationException(ErrorCode.TIMEOUT_UPDATE_ORDER);
        }


        OrderProduct orderProduct = orderProductRepository.findByOrderIdAndProductId(orderId, productId);
        log.info("orderProduct {}", orderProduct.toString());
        Long prevPrice = orderProduct.getQuantity() * orderProduct.getProduct().getPrice();


        Product product = productRepository.findById(orderProductRequestDto.getProductId())
                .orElseThrow(()->new TwotwoApplicationException(ErrorCode.PRODUCT_NOT_FOUND));
        orderProduct.changeProduct(product);
        orderProduct.changeQuantity(orderProductRequestDto.getQuantity());

        Long totalPrice = order.getPrice();

        order.setTotalPrice( totalPrice - prevPrice + (orderProduct.getProduct().getPrice() * orderProductRequestDto.getQuantity()));

        orderRepository.save(order);
        orderProductRepository.save(orderProduct);

        return order;

    }

    public void deleteOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));
        order.setDeletedBy(SecurityUtil.getMemberIdFromSecurityContext());
        order.setDeletedAt(LocalDateTime.now());
        orderRepository.save(order);
        orderRepository.flush();


        log.info("order {}", order.toString());
        // 주문 상품 삭제 정보 업데이트
        order.getOrderProducts().forEach(orderProduct -> {
            orderProduct.setDeletedBy(SecurityUtil.getMemberIdFromSecurityContext());
            orderProduct.setDeletedAt(LocalDateTime.now());
            orderProductRepository.save(orderProduct);
        });


        orderRepository.flush();

        orderRepository.deleteById(orderId);


    }

}
