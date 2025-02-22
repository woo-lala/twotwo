package com.sparta.twotwo.store.service;

import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Area;
import com.sparta.twotwo.store.repository.AddressRepository;
import com.sparta.twotwo.store.repository.AreaRepository;
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
    @DisplayName("Address 주소 생성 성공")
    void saveAddress() {
        // given
        Address reqAddress = Address.builder()
                .area(reqArea)
                .roadAddress("reqRoadAddress")
                .detailAddress("reqDetailAddress")
                .build();

        // when
        when(areaRepository.findAreaByZipNum(reqArea.getZipNum())).thenReturn(Optional.ofNullable(area));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        Address actualAddress = addressService.saveAddress(reqAddress);

        // then
        Assertions.assertEquals(address, actualAddress);
    }


    @Test
    @DisplayName("Area 존재할 때 주소 저장 성공")
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
    @DisplayName("Area 없을 때 주소 저장 성공")
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

}