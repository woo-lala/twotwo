package com.sparta.twotwo.order.entity;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OrderTest {


    @Autowired
    private OrderRepository repository;

    @Test
    @DisplayName("주문 생성")
    @Transactional
    public void save(){

        Order order = Order.builder()
                .order_type(OrderType.ONLINE)
                .price(20000L)
                .build();

        repository.save(order);

//        Assertions.assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("주문 삭제")
    @Transactional
    public void delete(){

        Order order = Order.builder()
                .order_type(OrderType.ONLINE)
                .price(1000L)
                .build();
        repository.save(order);
        repository.delete(order);
        Assertions.assertThat(repository.findAll()).hasSize(1);

    }

    @Test
    @DisplayName("주문 수정")
//    @Transactional
    public void update(){
        Order order = Order.builder()
                .order_type(OrderType.ONLINE)
                .price(1000L)
                .build();
        repository.save(order);

        Order find = repository.findById(order.getOrder_id()).orElseThrow(NullPointerException::new);

        find.changePrice(2000L);
        repository.save(find);

        Order update = repository.findById(find.getOrder_id()).orElseThrow(NullPointerException::new);
        Assertions.assertThat(update.getPrice()).isEqualTo(2000L);


    }




}