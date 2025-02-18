package com.sparta.twotwo.store.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "p_area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Area extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigg")
    private String sigg;

    @Column(name = "emd", nullable = false)
    private String emd;

    @Column(name = "adm_code", nullable = false)
    private String admCode;

    @Column(name = "zip_num", nullable = false)
    private String zipNum;

    @Builder
    public Area(String sido, String sigg, String emd, String admCode, String zipNum) {
        this.sido = sido;
        this.sigg = sigg;
        this.emd = emd;
        this.admCode = admCode;
        this.zipNum = zipNum;
    }

}
