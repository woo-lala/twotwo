package com.sparta.twotwo.store.service;

import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.store.dto.request.AddressRequest;
import com.sparta.twotwo.store.dto.request.StoreCreateRequest;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import com.sparta.twotwo.store.repository.AddressRepository;
import com.sparta.twotwo.store.repository.StoreCategoryRepository;
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
import java.time.LocalTime;
import java.util.*;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    @DisplayName("전체 가게 조회 성공")
    void getAllStores() {
        Pageable pageable = mock(Pageable.class);

        //given
        Store store1 = Store.builder()
                .name("가게 A")
                .minOrderPrice(0L)
                .rating(BigDecimal.valueOf(4.6))
                .reviewCount(10)
                .build();

        Store store2 = Store.builder()
                .name("가게 B")
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

    @Test
    @DisplayName("가게 상세 조회 성공")
    void getStoreDetails() {
        //given
        Store store = Store.builder()
                .name("가게 A")
                .minOrderPrice(0L)
                .imageUrl("store ImageUrl")
                .operationStartedAt(LocalTime.parse("14:00:00"))
                .operationClosedAt(LocalTime.parse("20:00:00"))
                .rating(BigDecimal.valueOf(4.6))
                .reviewCount(10)
                .build();

        //when
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
        Optional<Store> actualStore = storeService.getStoreDetails(store.getId());

        //then
        Assertions.assertEquals(Optional.of(store), actualStore);
    }

    @Test
    @DisplayName("카테고리별 가게 조회 성공")
    void getStoresByCategory() {

        Pageable pageable = mock(Pageable.class);

        //given
        StoreCategory category = StoreCategory.builder().name("카테고리 A").build();

        Store store1 = Store.builder()
                .name("가게 A")
                .category(category)
                .build();

        Store store2 = Store.builder()
                .name("가게 B")
                .category(category)
                .build();

        List<Store> storeList = new ArrayList<>();
        storeList.add(store1);
        storeList.add(store2);

        Page<Store> expectedPage = new PageImpl<>(storeList, pageable, storeList.size());

        //when
        when(storeCategoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(storeRepository.findStoresByCategoryId(category.getId(), pageable)).thenReturn(expectedPage);
        Page<Store> actualPage= storeService.getStoresByCategory(category.getId(),pageable);

        //then
        Assertions.assertEquals(expectedPage, actualPage);

    }

    @Test
    @DisplayName("중복된 이름인 가게 저장 실패")
    void duplicatedNameStore() {

        //given
        String duplicatedName = "가게 A";

        Store store = Store.builder()
                .name(duplicatedName)
                .build();

        Set<String> roles = new HashSet<>();
        roles.add("MASTER");

        Member member = new Member("username", "nickname", "master@email.com", "password", roles, TRUE);

        AddressRequest addressRequest = new AddressRequest("sido", "sigg", "emd", "admCode", "zipNum","roadAddress","detailAddress");

        Address address = Address.builder()
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();


        StoreCreateRequest request = StoreCreateRequest.builder()
                .name(duplicatedName)
                .memberId(member.getMember_id())
                .address(addressRequest)
                .build();

        StoreCategory category = StoreCategory.builder().name("카테고리 A").build();

        //when

        when(memberRepository.findById(request.getMemberId())).thenReturn(Optional.of(member));
        when(storeCategoryRepository.findById(request.getCategoryId())).thenReturn(Optional.of(category));
        when(storeRepository.findByName(duplicatedName)).thenReturn(Optional.of(store));

        //then
        assertThrows(TwotwoApplicationException.class, () -> {
            storeService.saveStore(request);
        });
    }



}
