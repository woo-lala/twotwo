package com.sparta.twotwo.order.controller;


import com.sparta.twotwo.auth.service.MemberDetails;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.order.dto.OrderProductRequestDto;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    /**
     * 모든 Order 조회
     * @param page
     * @param size
     * @param sortBy
     * @param isAsc
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<Page<OrderResponseDto>>> getOrders(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal Member member) {


        Page<OrderResponseDto> orders = orderService.getOrders(page - 1, size, sortBy, isAsc, member);

        return new ResponseEntity<>(ApiResponse.success(orders), HttpStatus.OK);

    }

    /**
     * Order 생성
     * @param orderRequestDto
     * @return
     */
    @PostMapping("/orders/{store_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @PathVariable("sotre_id") UUID store_id,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody OrderRequestDto orderRequestDto){

        log.info("requestDto: {}", orderRequestDto);
        OrderResponseDto orderResponseDto = orderService.saveOrder(orderRequestDto, memberDetails.getMember(), store_id);

        return new ResponseEntity<>(ApiResponse.success(orderResponseDto), HttpStatus.OK);
    }

    /**
     * OrderId별 조회
     * @param orderId
     * @return
     */
    @GetMapping("/orders/{order_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(@PathVariable("order_id") UUID orderId){
        OrderResponseDto response = orderService.getOrderById(orderId);
        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
    }


    //TODO
    // Order 수정
    @PatchMapping("/orders/{order_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(
            @PathVariable("order_id") UUID order_id,
            @RequestBody OrderRequestDto orderRequestDto,
            @RequestBody OrderProductRequestDto orderProductRequestDto){
        return null;
    }

    //TODO
    // Order 삭제
    @DeleteMapping("/orders/{order_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> deleteOrder(@PathVariable UUID order_id){
        return null;
    }



}
