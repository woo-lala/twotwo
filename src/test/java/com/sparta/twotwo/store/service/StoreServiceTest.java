package com.sparta.twotwo.store.service;

import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private StoreService storeService;

    @Test
    @DisplayName("전체 가게 조회 성공")
    void getAllStores() {
        Pageable pageable = mock(Pageable.class);

        //given
        Store store1 = Store.builder()
                .name("BHC치킨 한강공원난지2호점")
                .minOrderPrice(0L)
                .rating(BigDecimal.valueOf(4.6))
                .reviewCount(10)
                .build();

        Store store2 = Store.builder()
                .name("굽네치킨 상암점")
                .minOrderPrice(1000L)
                .rating(BigDecimal.valueOf(4.2))
                .reviewCount(1000)
                .build();

        List<Store> storeList = new ArrayList<>();
        storeList.add(store1);
        storeList.add(store2);

        Page<Store> expectedPage = new PageImpl<>(storeList, pageable, storeList.size());

        //when
        when(storeRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<Store> actualPage= storeService.getAllStores(pageable);

        //then
        Assertions.assertEquals(expectedPage, actualPage);

    }


}
