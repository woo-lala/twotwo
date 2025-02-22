package com.sparta.twotwo.order.dto;

import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.entity.OrderProduct;
import com.sparta.twotwo.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderProductRequestDto {

    private Long quantity;
    private UUID productId;

    public OrderProduct toEntity(Order order, Product product){
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .build();
    }

    @Override
    public String toString() {
        return "OrderProductRequestDto{" +
                "quantity=" + quantity +
                ", productId=" + productId +
                '}';
    }
}
