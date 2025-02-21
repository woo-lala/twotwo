package com.sparta.twotwo.order.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order")
public class Order extends BaseEntity {

    @Builder
    public Order(OrderType order_type, Long price, Member member, Store store, List<OrderProduct> orderProducts) {
        this.order_type = order_type;
        this.price = price;
        this.member = member;
        this.store = store;
        this.orderProducts = orderProducts;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="order_id", columnDefinition = "BINARY(16)")
    private UUID order_id;

    @Enumerated
    @Column(name = "order_type", nullable = false)
    private OrderType order_type;

    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderProduct> orderProducts;


    public void changeOrderTYpe(OrderType orderType) {
        this.order_type = orderType;
    }
    public void calculateTotalPrice(int price, Long quantity) {
        this.price = price * quantity;

    }


    public OrderResponseDto toResponseDto() {
        return OrderResponseDto.builder()
                .orderId(order_id)
                .orderType(order_type)
                .price(price)
                .memberId(member.getMember_id())
                .storeId(store.getId())
                .orderProduct(orderProducts.stream()
                        .map(OrderProduct::toDto)
                        .collect(Collectors.toList()))
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", order_type=" + order_type +
                ", price=" + price +
                ", member=" + member +
                ", store=" + store +
                ", orderProducts=" + orderProducts +
                '}';
    }


}
