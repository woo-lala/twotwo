package com.sparta.twotwo.order.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order")
public class Order extends BaseEntity {

    @Builder
    public Order(OrderType order_type, Long price) {
        this.order_type = order_type;
        this.price = price;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID order_id;

    @Enumerated
    @Column(name = "order_type")
    private OrderType order_type;

    @Column(name = "price")
    private Long price;

    public void changePrice(Long price) {
        this.price = price;
    }

    public void changeOrderType(OrderType order_type) {
        this.order_type = order_type;
    }


    public OrderResponseDto toResponseDto() {
        return OrderResponseDto.builder()
                .order_id(order_id)
                .order_type(order_type)
                .price(price)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

}
