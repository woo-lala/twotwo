package com.sparta.twotwo.order.entity;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "p_order")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID order_id;

    @Column(name = "order_type")
    private OrderType order_type;

    @Column(name = "price")
    private Long price;

    public OrderResponseDto toResponseDto() {
        return OrderResponseDto.builder()
                .order_id(order_id)
                .order_type(order_type)
                .price(price)
                .build();
    }

}
