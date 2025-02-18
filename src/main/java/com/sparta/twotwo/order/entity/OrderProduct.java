package com.sparta.twotwo.order.entity;


import com.sparta.twotwo.common.auditing.BaseEntity;
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
    public OrderProduct(Order order, Product product, Long product_Quantity){
        this.order = order;
        this.product = product;
        this.product_Quantity = product_Quantity;
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

    private Long product_Quantity;




}
