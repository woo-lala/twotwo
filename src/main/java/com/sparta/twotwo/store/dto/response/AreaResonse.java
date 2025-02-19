package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Area;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AreaResonse {

    private final UUID id;
    private final String sido;
    private final String sigg;
    private final String emd;
    private final String admCode;
    private final String zipNum;

    public static AreaResonse from(Area area) {
        return new AreaResonse(
                area.getId(),
                area.getSido(),
                area.getSigg(),
                area.getEmd(),
                area.getAdmCode(),
                area.getZipNum()
        );
    }
}