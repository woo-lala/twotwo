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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order")
public class Order extends BaseEntity {

    @Builder
    public Order(OrderType order_type, Long price, Member member, Store store) {
        this.orderType = order_type;
        this.price = price;
        this.member = member;
        this.store = store;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="order_id", columnDefinition = "BINARY(16)")
    private UUID orderId;

    @Enumerated
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Column(name = "price", nullable = false)
    private Long price;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "id")
    private Store store;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderProduct> orderProducts;


    public void changeOrderTYpe(OrderType orderType) {
        this.orderType = orderType;
    }


    public OrderResponseDto toResponseDto() {
        return OrderResponseDto.builder()
                .orderId(orderId)
                .orderType(orderType)
                .price(price)
                .memberId(member.getMember_id())
                .storeId(store.getId())
                .orderProduct(orderProducts)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

}
