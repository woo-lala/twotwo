package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.entity.OrderProduct;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.store.entity.Store;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {

    private OrderType orderType;
    private Long price;

    private Long quantity;
    private UUID productId;

    public Order toOrderEntity(Member member, Store store) {
        return Order.builder()
                .price(price)
                .order_type(orderType)
                .member(member)
                .store(store)
                .build();
    }

    public OrderProduct toOrderProductEntity(Order order, Product product){
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .build();
    }

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "orderType=" + orderType +
                ", price=" + price +
                ", quantity=" + quantity +
                ", productId=" + productId +
                '}';
    }
}
