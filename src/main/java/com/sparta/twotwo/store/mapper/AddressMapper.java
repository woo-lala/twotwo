package com.sparta.twotwo.store.mapper;

import com.sparta.twotwo.store.dto.request.AddressRequestDto;
import com.sparta.twotwo.store.dto.request.AddressUpdateRequest;
import com.sparta.twotwo.store.dto.request.AreaRequestDto;
import com.sparta.twotwo.store.dto.request.AreaUpdateRequestDto;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Area;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "area", source = "area")
    Address toAddress(AddressRequestDto requestDto);

    @Mapping(target = "area", source = "area")
    Address toAddress(AddressUpdateRequest requestDto);
    Area toArea(AreaRequestDto requestDto);
    Area toArea(AreaUpdateRequestDto requestDto);

}
