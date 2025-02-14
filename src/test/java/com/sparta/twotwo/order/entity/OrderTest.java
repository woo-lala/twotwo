package com.sparta.twotwo.order.entity;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OrderTest {


    @Autowired
    private OrderRepository repository;

    @Test
    @DisplayName("주문 생성")
    public void save(){

        Order order = Order.builder()
                .order_type(OrderType.ONLINE)
                .price(10000L)
                .build();

        repository.save(order);

        Assertions.assertThat(repository.findAll()).hasSize(1);

    }




}