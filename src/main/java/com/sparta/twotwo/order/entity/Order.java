package com.sparta.twotwo.order.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.order.dto.OrderResponseDto;
import com.sparta.twotwo.store.entity.Store;
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
    public Order(OrderType order_type, Long price, Member member, Store store) {
        this.order_type = order_type;
        this.price = price;
        this.member = member;
        this.store = store;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
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
    @JoinColumn(name = "id")
    private Store store;


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
                .memberId(member.getMember_id())
                .storeId(store.getId())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

}
