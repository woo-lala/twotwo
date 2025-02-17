package com.sparta.twotwo.product.entity;

import com.sparta.twotwo.ai.entity.AIRequestLog;
import com.sparta.twotwo.common.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    // 나중에 외래 키 설정 수정하기

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "description_id")
    private AIRequestLog descriptionLog;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "image_url", length = 200)
    private String imageUrl;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden = false;
}