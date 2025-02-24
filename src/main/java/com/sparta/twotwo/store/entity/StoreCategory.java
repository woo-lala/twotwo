package com.sparta.twotwo.store.entity;


import com.sparta.twotwo.common.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
@Table(name = "p_store_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_deleted = false")
public class StoreCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Store> stores = new ArrayList<>();

    @Builder
    public StoreCategory(String name) {
        this.name = name;
    }

}
