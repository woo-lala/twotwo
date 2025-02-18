package com.sparta.twotwo.store.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.members.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="p_store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name="address_id", nullable = false)
    private Address address;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="category_id", nullable = false)
//    private Category category;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="name")
    private String name;

    @Column(name="min_order_price")
    private Long minOrderPrice;

    @Column(name="operation_started_at")
    private LocalDateTime operationStartedAt;

    @Column(name="operation_closed_at")
    private LocalDateTime operationClosedAt;

    @Column(name="rating")
    private BigDecimal rating;

    @Column(name="review_cnt")
    private Integer reviewCount;


}
