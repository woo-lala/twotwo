package com.sparta.twotwo.order.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.members.entity.QMember;
import com.sparta.twotwo.order.dto.SearchRequestDto;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.entity.QOrder;
import com.sparta.twotwo.order.entity.QOrderProduct;
import com.sparta.twotwo.store.entity.QStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
                .leftJoin(order.orderProducts, orderProduct)
                .fetchJoin()
                .where(order.order_id.eq(orderId))
                .fetchOne();

    }

    @Override
    public Page<Order> searchOrders(SearchRequestDto searchRequestDto, Pageable pageable) {
        QOrder order = QOrder.order;
        QStore store = QStore.store;
        QMember member = QMember.member;


        BooleanBuilder builder = new BooleanBuilder();
        if(searchRequestDto!=null){
            if(searchRequestDto.getOrderId() != null){
                System.out.println(searchRequestDto.getOrderId());
                builder.and(order.order_id.eq(UUID.fromString(searchRequestDto.getOrderId())));
            }
            if(searchRequestDto.getStoreId() != null){
                System.out.println(searchRequestDto.getStoreId());
                builder.and(store.id.eq(UUID.fromString(searchRequestDto.getStoreId())));
            }
            if(searchRequestDto.getMemberId() != null){
                System.out.println(searchRequestDto.getMemberId());
                builder.and(member.member_id.eq(searchRequestDto.getMemberId()));
            }
            if(searchRequestDto.getOrderType() != null){
                System.out.println(searchRequestDto.getOrderType());
                builder.and(order.order_type.eq(searchRequestDto.getOrderType()));
            }

        }

        QueryResults<Order> results = queryFactory
                .selectFrom(order)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order.createdAt.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());

    }
}
