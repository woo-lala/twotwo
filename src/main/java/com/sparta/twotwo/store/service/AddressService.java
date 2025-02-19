package com.sparta.twotwo.store.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.store.dto.request.AddressRequest;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Area;
import com.sparta.twotwo.store.repository.AddressRepository;
import com.sparta.twotwo.store.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AreaRepository areaRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Address saveAddress(AddressRequest request) {

        //Area 있으면 가져오고 없으면 새로 생성
        Area area = areaRepository.findAreaByZipNum(request.getZipNum()).orElseGet(
                () -> Area.builder()
                        .zipNum(request.getZipNum())
                        .emd(request.getEmd())
                        .sido(request.getSido())
                        .sigg(request.getSigg())
                        .emd(request.getEmd())
                        .admCode(request.getAdmCode())
                        .build()
        );
        areaRepository.save(area);

        //주소 생성
        Address newAddress = Address.builder()
                .roadAddress(request.getRoadAddress())
                .detailAddress(request.getDetailAddress())
                .area(area)
                .build();

        final Address resultAddress = addressRepository.save(newAddress);
        return resultAddress;
    }

}
