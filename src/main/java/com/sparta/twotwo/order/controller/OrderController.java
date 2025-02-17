package com.sparta.twotwo.order.controller;


import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam("isAsc") boolean isAsc){

        //TODO @AuthenticationPrinciple 매개변수 추가

        Page<OrderResponseDto> orders = orderService.getOrders(page - 1, size, sortBy, isAsc);

        return new ResponseEntity<>(ApiResponse.success(orders), HttpStatus.OK);

    }

    /**
     * Order 생성
     * @param requestDto
     * @return
     */
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto requestDto){
        log.info("requestDto: {}", requestDto);
        OrderResponseDto orderResponseDto = orderService.saveOrder(requestDto);

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
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(@PathVariable("order_id") UUID order_id){
        return null;
    }

    //TODO
    // Order 삭제
    @DeleteMapping("/orders/{order_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> deleteOrder(@PathVariable UUID order_id){
        return null;
    }



}
