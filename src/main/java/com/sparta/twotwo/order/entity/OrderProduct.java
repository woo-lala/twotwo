package com.sparta.twotwo.order.entity;


import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.dto.OrderProductDto;
import com.sparta.twotwo.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "p_orderProduct")
public class OrderProduct extends BaseEntity {

    @Builder
    public OrderProduct(Order order, Product product, Long quantity){
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID orderProductId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Long quantity;


    public void changeQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public OrderProductDto toDto(){
        return OrderProductDto.builder()
                .orderProductId(orderProductId)
                .order(order.getOrder_id())
                .product(product.getId())
                .quantity(quantity)
                .build();
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderProductId=" + orderProductId +
                ", quantity=" + quantity +
                '}';
    }
}
