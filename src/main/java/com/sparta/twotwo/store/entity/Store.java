package com.sparta.twotwo.store.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name="p_store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name="address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_category_id", nullable = false)
    private StoreCategory category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="min_order_price")
    private Long minOrderPrice;

    @Column(name="operation_started_at")
    private LocalTime operationStartedAt;

    @Column(name="operation_closed_at")
    private LocalTime operationClosedAt;

    @Column(name="rating")
    private BigDecimal rating;

    @Column(name="review_cnt")
    private Integer reviewCount;

    @Builder
    public Store(Member member, Address address, String name, Long minOrderPrice, LocalTime operationClosedAt, LocalTime operationStartedAt, StoreCategory category, BigDecimal rating, Integer reviewCount, String imageUrl) {
        this.address = address;
        this.minOrderPrice = minOrderPrice;
        this.member = member;
        this.category = category;
        this.operationClosedAt = operationClosedAt;
        this.operationStartedAt = operationStartedAt;
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }


}
