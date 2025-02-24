package com.sparta.twotwo.address.mapper;

import com.sparta.twotwo.address.dto.request.AddressRequestDto;
import com.sparta.twotwo.address.dto.request.AddressUpdateRequest;
import com.sparta.twotwo.address.dto.request.AreaRequestDto;
import com.sparta.twotwo.address.dto.request.AreaUpdateRequestDto;
import com.sparta.twotwo.address.entity.Address;
import com.sparta.twotwo.address.entity.Area;
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
