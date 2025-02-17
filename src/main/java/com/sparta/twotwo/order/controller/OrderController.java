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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<Page<OrderResponseDto>>> getOrders(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc){

        Page<OrderResponseDto> orders = orderService.getOrders(page - 1, size, sortBy, isAsc);

        return new ResponseEntity<>(ApiResponse.success(orders), HttpStatus.OK);

    }

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto requestDto){
        log.info("requestDto: {}", requestDto);
        OrderResponseDto orderResponseDto = orderService.saveOrder(requestDto);

        return new ResponseEntity<>(ApiResponse.success(orderResponseDto), HttpStatus.OK);
    }




}
