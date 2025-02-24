package com.sparta.twotwo.address.service;

import com.sparta.twotwo.address.service.AddressService;
import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.address.entity.Address;
import com.sparta.twotwo.address.entity.Area;
import com.sparta.twotwo.address.repository.AddressRepository;
import com.sparta.twotwo.address.repository.AreaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AreaRepository areaRepository;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressService addressService;

    private Address address;
    private Area area;
    private Area reqArea;

    @BeforeEach
    public void setUp() {

        area = Area.builder()
                .sido("sido")
                .sigg("sigg")
                .emd("emd")
                .admCode("admcode")
                .zipNum("zipNum")
                .build();

        address = Address.builder()
                .area(area)
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();

        reqArea = Area.builder()
                .sido("reqSido")
                .sigg("reqSigg")
                .emd("reqEmd")
                .admCode("reqAdmcode")
                .zipNum("reqZipNum")
                .build();
    }


    @Test
    @DisplayName("Address 생성 성공")
    void saveAddress() {
        // given
        Address reqAddress = Address.builder()
                .area(reqArea)
                .roadAddress("reqRoadAddress")
                .detailAddress("reqDetailAddress")
                .build();
        when(areaRepository.findAreaByZipNum(reqArea.getZipNum())).thenReturn(Optional.ofNullable(area));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // when
        Address actualAddress = addressService.saveAddress(reqAddress);

        // then
        Assertions.assertEquals(address, actualAddress);
    }

    @Test
    @DisplayName("중복된 Address 저장 예외 발생")
    void saveDuplicatedAddress() {
        // given
        Area sameReqArea = Area.builder()
                .sido("sido")
                .sigg("sigg")
                .emd("emd")
                .admCode("admcode")
                .zipNum("zipNum")
                .build();

        Address sameReqAddress = Address.builder()
                .area(sameReqArea)
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();
        when(areaRepository.findAreaByZipNum(address.getArea().getZipNum())).thenReturn(Optional.ofNullable(area));
        when(addressRepository.findByAreaIdAndRoadAddressAndDetailAddress(area.getId(),
                        sameReqAddress.getRoadAddress(),
                        sameReqAddress.getDetailAddress()
                )
        ).thenReturn(Optional.ofNullable(address));

        // when&then
        TwotwoApplicationException exception = Assertions.assertThrows(TwotwoApplicationException.class, () ->
                addressService.saveAddress(sameReqAddress)
        );

        Assertions.assertEquals(ErrorCode.ADDRESS_EXIST, exception.getErrorCode());
    }


    @Test
    @DisplayName("Area 존재할 때 Address 저장 성공")
    void saveAddressIfAreaExists() {
        // given
        Address reqAddress = Address.builder()
                .area(reqArea)
                .roadAddress("reqRoadAddress")
                .detailAddress("reqDetailAddress")
                .build();

        when(areaRepository.findAreaByZipNum(reqArea.getZipNum())).thenReturn(Optional.ofNullable(area));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // when
        Address actualAddress = addressService.saveAddress(reqAddress);

        // then
        Assertions.assertEquals(address, actualAddress);
        verify(areaRepository, never()).save(any(Area.class));

    }

    @Test
    @DisplayName("Area 없을 때 Address 저장 성공")
    void saveAddressIfAreaDoesNotExist() {
        // given
        Address reqAddress = Address.builder()
                .area(reqArea)
                .roadAddress("reqRoadAddress")
                .detailAddress("reqDetailAddress")
                .build();

        // when
        when(areaRepository.findAreaByZipNum(reqArea.getZipNum())).thenReturn(Optional.empty());
        when(areaRepository.save(any(Area.class))).thenReturn(area);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address actualAddress = addressService.saveAddress(reqAddress);

        // then
        Assertions.assertEquals(address, actualAddress);
        verify(areaRepository, times(1)).save(any(Area.class));
    }

    @Test
    @DisplayName("Area 없으면 Area 수정되지 않고 Address 수정 성공")
    void updateAddressIfAreaDoesNotExist() {
        // given
        Address reqAddress = Address.builder()
                .roadAddress("reqRoadAddress")
                .detailAddress("reqDetailAddress")
                .build();

        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // when
        Address actualAddress = addressService.updateAddress(address, reqAddress);

        // then
        Assertions.assertEquals(address.getArea(), actualAddress.getArea());
        verify(areaRepository, never()).save(any(Area.class));

    }

    @Test
    @DisplayName("기존 Address과 같으면 예외 발생")
    void updateAddressIfAddressIsSame() {
        // given
        Address sameReqAddress = Address.builder()
                .area(area)
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();

        when(areaRepository.findAreaByZipNum(sameReqAddress.getArea().getZipNum())).thenReturn(Optional.ofNullable(area));

        // when & then
        TwotwoApplicationException exception = Assertions.assertThrows(TwotwoApplicationException.class, () ->
                addressService.updateAddress(address, sameReqAddress));
        Assertions.assertEquals(ErrorCode.NO_ADDRESS_CHANGES, exception.getErrorCode());
    }

    @Test
    @DisplayName("Address 일부만 변경해도 Address 저장 성공")
    void updateAddressIfAddressPartiallyChanges() {
        // given
        Address reqAddress = Address.builder()
                .roadAddress("updateRoadAddress")
                .build();

        Address updatedAddress = Address.builder()
                .area(area)
                .roadAddress("updateRoadAddress")
                .detailAddress("detailAddress")
                .build();

        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        // when
        Address actualAddress = addressService.updateAddress(address, reqAddress);

        // then
        Assertions.assertEquals("updateRoadAddress", actualAddress.getRoadAddress());
        verify(addressRepository, times(1)).save(any(Address.class));
    }


}