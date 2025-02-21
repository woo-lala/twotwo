package com.sparta.twotwo.order.controller;


import com.sparta.twotwo.auth.service.MemberDetails;
import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.dto.OrderProductRequestDto;
import com.sparta.twotwo.order.dto.OrderRequestDto;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
            @RequestParam("isAsc") boolean isAsc
//            @AuthenticationPrincipal Member member
    ) {

        Long memberId = SecurityUtil.getMemberIdFromSecurityContext();
        Member member = memberRepository.findById(memberId).orElseThrow();

        Page<Order> orders = orderService.getOrders(page - 1, size, sortBy, isAsc, member);

        Page<OrderResponseDto> response = orders.map(Order::toResponseDto);

        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);

    }

    /**
     * Order 생성
     */
    @PostMapping("/orders/{store_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @PathVariable("store_id") String store_id,
//            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody OrderRequestDto orderRequestDto){



//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MemberDetails memberDetails = (MemberDetails)authentication.getPrincipal();

        Long memberId = SecurityUtil.getMemberIdFromSecurityContext();
        log.info("memberId {}", memberId);
        Member member = memberRepository.findById(memberId).orElseThrow();
        log.info("member {}", member);

        log.info("requestDto: {}", orderRequestDto);
//        log.info("memberDetails: {}", memberDetails);


        UUID store = UUID.fromString(store_id);
        OrderResponseDto orderResponseDto = orderService.saveOrder(orderRequestDto, member, store);

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
        OrderResponseDto responseDto = findOrder.toResponseDto();
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }


    //TODO
    // Order 수정
    @PatchMapping("/orders/{order_id}/{product_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(
            @PathVariable("order_id") UUID order_id,
            @PathVariable("product_id") UUID product_id,
            @RequestBody OrderRequestDto orderRequestDto){
        log.info("orderId {}", order_id);
        log.info("productId {}", product_id);
        log.info("orderRequestDto {}", orderRequestDto);
        Order order = orderService.updateOrder(order_id, product_id, orderRequestDto);
        return new ResponseEntity<>(ApiResponse.success(order.toResponseDto()), HttpStatus.OK);
    }

    //TODO
    // Order 삭제
    @DeleteMapping("/orders/{order_id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> deleteOrder(@PathVariable UUID order_id){
        return null;
    }



}
