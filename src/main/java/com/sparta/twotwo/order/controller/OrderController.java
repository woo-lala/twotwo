package com.sparta.twotwo.order.controller;


import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.dto.OrderProductRequestDto;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.dto.SearchRequestDto;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final MemberRepository memberRepository;

    /**
     * 모든 Order 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<Page<OrderResponseDto>>> getOrders(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @ModelAttribute SearchRequestDto searchRequestDto
    ) {
        log.info("searchRequestDto: {}", searchRequestDto);

        Page<Order> orders = orderService.getOrders(page - 1, size, sortBy, isAsc, searchRequestDto);

        Page<OrderResponseDto> response = orders.map(Order::toOrderResponseDto);

        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);

    }

    /**
     * Order 생성
     */
    @PostMapping("/orders/{store_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @PathVariable("store_id") String store_id,
            @RequestBody OrderRequestDto orderRequestDto
    ){
        UUID storeId = UUID.fromString(store_id);
        OrderResponseDto orderResponseDto = orderService.saveOrder(orderRequestDto, storeId);

        return new ResponseEntity<>(ApiResponse.success(orderResponseDto), HttpStatus.OK);
    }

    /**
     * OrderId별 조회
     * @param orderId
     * @return
     */
    @GetMapping("/orders/{order_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(@PathVariable("order_id") UUID orderId){
        Order findOrder = orderService.getOrderById(orderId);
        OrderResponseDto responseDto = findOrder.toDetailResponseDto();
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }


    @PatchMapping("/orders/{order_id}/{product_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(
            @PathVariable("order_id") UUID order_id,
            @PathVariable("product_id") UUID product_id,
            @RequestBody OrderProductRequestDto orderProductRequestDto){
        Order order = orderService.updateOrder(order_id, product_id, orderProductRequestDto);
        return new ResponseEntity<>(ApiResponse.success(order.toDetailResponseDto()), HttpStatus.OK);
    }


    @DeleteMapping("/orders/{order_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> deleteOrder(@PathVariable("order_id") UUID order_id){
        orderService.deleteOrder(order_id);
        return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
    }



}
