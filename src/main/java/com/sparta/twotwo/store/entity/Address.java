package com.sparta.twotwo.store.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="area_id", nullable = false)
    private Area area;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Builder
    public Address(Area area, String roadAddress, String detailAddress) {
        this.area = area;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

}
