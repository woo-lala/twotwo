package com.sparta.twotwo.pay.entity;


import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.enums.PayMethod;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@SQLDelete(sql = "UPDATE p_payment SET is_deleted=true WHERE payment_id= ?")
@SQLRestriction("is_deleted = false")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "p_payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="payment_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "method")
    private PayMethod method;

    @Column(name = "price")
    private Long price;






}
