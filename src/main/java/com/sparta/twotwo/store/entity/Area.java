package com.sparta.twotwo.store.entity;

import com.sparta.twotwo.common.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Entity
@Table(name = "p_area")
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Area extends BaseEntity {

//    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigg")
    private String sigg;

    @Column(name = "emd", nullable = false)
    private String emd;

    @Column(name = "adm_code", nullable = false)
    private String admCode;

    @Column(name = "zip_num", nullable = false, unique = true)
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
