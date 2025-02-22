package com.sparta.twotwo.store.service;

import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.store.dto.request.AddressRequestDto;
import com.sparta.twotwo.store.dto.request.StoreCreateRequestDto;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import com.sparta.twotwo.store.repository.AddressRepository;
import com.sparta.twotwo.store.repository.StoreCategoryRepository;
import com.sparta.twotwo.store.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.time.LocalTime;
import java.util.*;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreCategoryRepository storeCategoryRepository;
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressService addressService;

    @InjectMocks
    private StoreService storeService;

    private List<Store> stores = new ArrayList<>();
    private Store storeA;
    private Store storeB;
    private StoreCategory category;
    private Member member;
    @BeforeEach
    private void setUp() {

        Set<String> roles = new HashSet<>();
        roles.add("MASTER");
        member = new Member("username", "nickname", "master@email.com", "password", roles, TRUE);

        category = StoreCategory.builder().name("category").build();

        storeA = Store.builder()
                .name("storeA")
                .minOrderPrice(0L)
                .imageUrl("http://storeA.jpg")
                .operationStartedAt(LocalTime.parse("14:00:00"))
                .operationClosedAt(LocalTime.parse("22:00:00"))
                .category(category)
                .build();

        storeB = Store.builder()
                .name("storeB")
                .minOrderPrice(1000L)
                .imageUrl("http://storeB.jpg")
                .operationStartedAt(LocalTime.parse("10:00:00"))
                .operationClosedAt(LocalTime.parse("20:00:00"))
                .category(category)
                .build();

        stores.add(storeA);
        stores.add(storeB);

    }

    @Test
    @DisplayName("전체 가게 조회 성공")
    void getAllStores() {

        //given
        Pageable pageable = mock(Pageable.class);
        Page<Store> expectedPage = new PageImpl<>(stores, pageable, stores.size());
        when(storeRepository.findAll(pageable)).thenReturn(expectedPage);

        //when
        Page<Store> actualPage= storeService.getAllStores(pageable);

        //then
        Assertions.assertEquals(expectedPage, actualPage);

    }

    @Test
    @DisplayName("가게 상세 조회 성공")
    void getStoreDetails() {
        //given
        when(storeRepository.findById(storeA.getId())).thenReturn(Optional.of(storeA));

        //when
        Optional<Store> actualStore = storeService.getStoreDetails(storeA.getId());

        //then
        Assertions.assertEquals(Optional.of(storeA), actualStore);
    }

    @Test
    @DisplayName("카테고리별 가게 조회 성공")
    void getStoresByCategory() {

        //given
        Pageable pageable = mock(Pageable.class);
        Page<Store> expectedPage = new PageImpl<>(stores, pageable, stores.size());
        when(storeCategoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(storeRepository.findStoresByCategoryId(category.getId(), pageable)).thenReturn(expectedPage);

        //when
        Page<Store> actualPage= storeService.getStoresByCategory(category.getId(),pageable);

        //then
        Assertions.assertEquals(expectedPage, actualPage);

    }

    @Test
    @DisplayName("중복된 이름인 가게 저장 실패")
    void duplicatedNameStore() {

        //given
        Address reqAddress = Address.builder()
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();

        String duplicatedName = "storeA";

        Store reqStore = Store.builder()
                .name(duplicatedName)
                .category(category)
                .member(member)
                .address(reqAddress)
                .build();

        when(memberRepository.findById(reqStore.getMember().getMember_id())).thenReturn(Optional.of(member));
        when(addressService.saveAddress(reqStore.getAddress())).thenReturn(reqAddress);
        when(storeCategoryRepository.findById(reqStore.getCategory().getId())).thenReturn(Optional.of(category));
        when(storeRepository.findByName(reqStore.getName())).thenReturn(Optional.of(storeA));

        //when&then
        assertThrows(TwotwoApplicationException.class, () -> {
            storeService.saveStore(reqStore);
        });
    }



}
