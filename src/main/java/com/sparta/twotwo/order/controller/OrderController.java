package com.sparta.twotwo.order.controller;


import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponseDto>> getOrders(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc){

        Page<OrderResponseDto> orders = orderService.getOrders(page - 1, size, sortBy, isAsc);

        return new ResponseEntity<>(orders, HttpStatus.OK);


    }


}
