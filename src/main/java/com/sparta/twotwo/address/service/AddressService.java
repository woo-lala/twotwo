package com.sparta.twotwo.address.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.address.entity.Address;
import com.sparta.twotwo.address.entity.Area;
import com.sparta.twotwo.address.repository.AddressRepository;
import com.sparta.twotwo.address.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AreaRepository areaRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Address saveAddress(Address reqAddress) {

        Area area = getOrCreateArea(reqAddress.getArea());

        Optional<Address> existingAddress = addressRepository.findByAreaIdAndRoadAddressAndDetailAddress(
                area.getId(),
                reqAddress.getRoadAddress(),
                reqAddress.getDetailAddress());

        if (existingAddress.isPresent()) {
            throw new TwotwoApplicationException(ErrorCode.ADDRESS_EXIST);
        }

        Address newAddress = Address.builder()
                .area(area)
                .roadAddress(reqAddress.getRoadAddress())
                .detailAddress(reqAddress.getDetailAddress())
                .build();

        return addressRepository.save(newAddress);
    }

    @Transactional
    public Address updateAddress(Address existingAddress, Address reqAddress) {

        Area area = (reqAddress.getArea() != null) ? getOrCreateArea(reqAddress.getArea()) : existingAddress.getArea();

        if (area.equals(existingAddress.getArea()) &&
                reqAddress.getRoadAddress().equals(existingAddress.getRoadAddress()) &&
                reqAddress.getDetailAddress().equals(existingAddress.getDetailAddress())) {
            throw new TwotwoApplicationException(ErrorCode.NO_ADDRESS_CHANGES);
        }

        existingAddress.updateArea(area);
        Optional.ofNullable(reqAddress.getRoadAddress()).ifPresent(existingAddress::updateRoadAddress);
        Optional.ofNullable(reqAddress.getDetailAddress()).ifPresent(existingAddress::updateDetailAddress);


        return addressRepository.save(existingAddress);
    }

    @Transactional
    public Area getOrCreateArea(Area reqArea) {
        return areaRepository.findAreaByZipNum(reqArea.getZipNum()).orElseGet(
                () -> areaRepository.save(
                        Area.builder()
                                .zipNum(reqArea.getZipNum())
                                .emd(reqArea.getEmd())
                                .sido(reqArea.getSido())
                                .sigg(reqArea.getSigg())
                                .emd(reqArea.getEmd())
                                .admCode(reqArea.getAdmCode())
                                .build()
                )
        );
    }

}
