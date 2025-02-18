package com.sparta.twotwo.store.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.members.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
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
//    @JoinColumn(name="store_category_id", nullable = false)
//    private StoreCategory category;

//    @OneToMany(mappedBy="product")
//    @JoinColumn(name="product_id")
//    private List<Product> products = new ArrayList<>();

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="name")
    private String name;

    @Column(name="min_order_price")
    private Long minOrderPrice;

//    @Column(name="operation_started_at")
//    private LocalDateTime operationStartedAt;
//
//    @Column(name="operation_closed_at")
//    private LocalDateTime operationClosedAt;

    @Column(name="rating")
    private BigDecimal rating;

    @Column(name="review_cnt")
    private Integer reviewCount;

    //TODO: add operation Started and Closed Time
    @Builder
    public Store(Member member, Address address, String name, Long minOrderPrice, BigDecimal rating, Integer reviewCount) {
        this.address = address;
        this.minOrderPrice = minOrderPrice;
        this.member = member;
//        this.operationClosedAt = operationClosedAt;
//        this.operationStartedAt = operationStartedAt;
        this.name = name;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }


}
