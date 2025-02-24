package com.sparta.twotwo.address.dto.response;

import com.sparta.twotwo.store.entity.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AreaResonseDto {

    private final UUID id;
    private final String sido;
    private final String sigg;
    private final String emd;
    private final String admCode;
    private final String zipNum;

    public static AreaResonseDto from(Area area) {
        return new AreaResonseDto(
                area.getId(),
                area.getSido(),
                area.getSigg(),
                area.getEmd(),
                area.getAdmCode(),
                area.getZipNum()
        );
    }
}