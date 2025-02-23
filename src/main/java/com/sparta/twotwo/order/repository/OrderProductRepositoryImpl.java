package com.sparta.twotwo.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twotwo.order.entity.OrderProduct;
import com.sparta.twotwo.order.entity.QOrder;
import com.sparta.twotwo.order.entity.QOrderProduct;
import com.sparta.twotwo.product.entity.QProduct;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public OrderProduct findByOrderIdAndProductId(UUID orderId, UUID productId) {

        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QOrder order = QOrder.order;
        QProduct product = QProduct.product;

        return queryFactory
                .selectFrom(orderProduct)
                .where(
                        orderProduct.order.order_id.eq(orderId)
                        .and(orderProduct.product.id.eq(productId))
                )
                .fetchOne();
    }
}
