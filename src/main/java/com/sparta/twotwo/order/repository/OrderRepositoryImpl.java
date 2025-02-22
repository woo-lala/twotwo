package com.sparta.twotwo.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.entity.QOrder;
import com.sparta.twotwo.order.entity.QOrderProduct;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements  OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Order findByOrderId(UUID orderId) {

        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        return queryFactory
                .selectFrom(order)
                .leftJoin(order.orderProducts, orderProduct)  // 연관된 orderProducts와 left join
                .fetchJoin()  // fetchJoin()을 사용하여 N+1 문제 방지
                .where(order.order_id.eq(orderId))  // order_id로 필터링
                .fetchOne();  // 단일 객체 반환

    }
}
