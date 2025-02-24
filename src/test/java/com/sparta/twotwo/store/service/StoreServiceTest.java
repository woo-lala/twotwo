package com.sparta.twotwo.store.service;

import com.sparta.twotwo.address.service.AddressService;
import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.address.entity.Address;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
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

import java.time.LocalTime;
import java.util.*;

import static java.lang.Boolean.TRUE;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private SecurityUtil securityUtil;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreCategoryRepository storeCategoryRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private AddressService addressService;

    @InjectMocks
    private StoreService storeService;

    private List<Store> stores = new ArrayList<>();
    private Store storeA;
    private Store storeB;
    private StoreCategory category;
    private Member member;
    private Address address;
    @BeforeEach
    private void setUp() {

        Set<String> roles = new HashSet<>();
        roles.add("MASTER");
        roles.add("OWNER");
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

        address = Address.builder()
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();

    }

    @Test
    @DisplayName("전체 가게 조회 성공")
    void getAllStores() {
        // given
        Pageable pageable = mock(Pageable.class);
        Page<Store> expectedPage = new PageImpl<>(stores, pageable, stores.size());
        when(storeRepository.findAll(pageable)).thenReturn(expectedPage);

        // when
        Page<Store> actualPage= storeService.getAllStores(pageable);

        // then
        Assertions.assertEquals(expectedPage, actualPage);

    }

    @Test
    @DisplayName("가게 상세 조회 성공")
    void getStoreDetails() {
        // given
        when(storeRepository.findById(storeA.getId())).thenReturn(Optional.of(storeA));

        // when
        Store actualStore = storeService.getStoreDetails(storeA.getId());

        // then
        Assertions.assertEquals(storeA, actualStore);
    }

    @Test
    @DisplayName("존재하지 않는 가게 상세 조회 예외 발생")
    void getStoreDetailsIfStoreNotExists() {
        // given
        Store reqStore = Store.builder().build();
        when(storeRepository.findById(reqStore.getId())).thenReturn(Optional.empty());

        // when
        TwotwoApplicationException exception = Assertions.assertThrows(TwotwoApplicationException.class, () -> {
            storeService.getStoreDetails(reqStore.getId());
        });

        // then
        Assertions.assertEquals(ErrorCode.STORE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("카테고리별 가게 조회 성공")
    void getStoresByCategory() {
        // given
        Pageable pageable = mock(Pageable.class);
        Page<Store> expectedPage = new PageImpl<>(stores, pageable, stores.size());
        when(storeCategoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(storeRepository.findStoresByCategoryId(category.getId(), pageable)).thenReturn(expectedPage);

        // when
        Page<Store> actualPage= storeService.getStoresByCategory(category.getId(),pageable);

        // then
        Assertions.assertEquals(expectedPage, actualPage);

    }

    @Test
    @DisplayName("중복된 이름인 가게 저장 예외 발생")
    void saveDuplicatedNameStore() {
        // given
        String duplicatedName = "storeA";

        Store reqStore = Store.builder()
                .name(duplicatedName)
                .category(category)
                .member(member)
                .address(address)
                .build();

        when(memberRepository.findById(reqStore.getMember().getMember_id())).thenReturn(Optional.of(member));
        when(addressService.saveAddress(reqStore.getAddress())).thenReturn(address);
        when(storeCategoryRepository.findById(reqStore.getCategory().getId())).thenReturn(Optional.of(category));
        when(storeRepository.findByName(reqStore.getName())).thenReturn(Optional.of(storeA));

        //when&then
        Assertions.assertThrows(TwotwoApplicationException.class, () -> {
            storeService.saveStore(reqStore);
        });
    }

    @Test
    @DisplayName("관리자가 아니고 가게 주인이 아닐 때 Store 수정 시도 시 예외 발생")
    void updateStoreWhenNotOwner() {
        // given
        UUID storeId = UUID.randomUUID();
        Long ownerId = 1L;
        Long modifierId = 2L;
        Set<String> newRoles = new HashSet<>();
        newRoles.add("OWNER");
        newRoles.add("CUSTOMER");

        Member modifier = new Member("username", "nickname", "owner@email.com", "password", newRoles, TRUE);
        modifier.setMember_id(modifierId);

        Member owner = new Member("username", "nickname", "owner@email.com", "password", newRoles, TRUE);
        owner.setMember_id(ownerId);

        Store reqStore = Store.builder()
                .member(modifier)
                .name("newName")
                .build();

        Store store = Store.builder()
                .name("store")
                .member(owner)
                .build();

        when(securityUtil.getMemberId()).thenReturn(modifierId);
        when(memberRepository.findById(modifierId)).thenReturn(Optional.of(modifier));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        // when&then
        TwotwoApplicationException exception = Assertions.assertThrows(TwotwoApplicationException.class, () -> {
            storeService.updateStore(storeId, reqStore);
        });
        Assertions.assertEquals(ErrorCode.UNAUTHORIZED, exception.getErrorCode());
    }

    @Test
    @DisplayName("관리자가 아니고 가게 주인이 아닐 때 Store 삭제 시도 시 예외 발생")
    void deleteStoreWhenNotOwner() {
        // given
        UUID storeId = UUID.randomUUID();
        Long ownerId = 1L;
        Long deleterId = 2L;
        Set<String> newRoles = new HashSet<>();
        newRoles.add("OWNER");
        newRoles.add("CUSTOMER");

        Member deleter = new Member("username", "nickname", "owner@email.com", "password", newRoles, TRUE);
        deleter.setMember_id(deleterId);

        Member owner = new Member("username", "nickname", "owner@email.com", "password", newRoles, TRUE);
        owner.setMember_id(ownerId);

        Store store = Store.builder()
                .name("store")
                .member(owner)
                .build();

        when(securityUtil.getMemberId()).thenReturn(deleterId);
        when(memberRepository.findById(deleterId)).thenReturn(Optional.of(deleter));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        // when&then
        TwotwoApplicationException exception = Assertions.assertThrows(TwotwoApplicationException.class, () -> {
            storeService.deleteStore(storeId);
        });
        Assertions.assertEquals(ErrorCode.UNAUTHORIZED, exception.getErrorCode());
    }

    @Test
    @DisplayName("Store 삭제 성공")
    void deleteStore() {
        // given
        UUID storeId = UUID.randomUUID();
        Long ownerId = 1L;
        Long deleterId = 1L;
        Set<String> newRoles = new HashSet<>();
        newRoles.add("MANAGER");
        newRoles.add("OWNER");

        Member deleter = new Member("username", "nickname", "owner@email.com", "password", newRoles, TRUE);
        deleter.setMember_id(deleterId);

        Member owner = new Member("username", "nickname", "owner@email.com", "password", newRoles, TRUE);
        owner.setMember_id(ownerId);

        Store store = Store.builder()
                .name("store")
                .member(owner)
                .build();
        store.setIsDeleted(false);

        when(securityUtil.getMemberId()).thenReturn(deleterId);
        when(memberRepository.findById(deleterId)).thenReturn(Optional.of(deleter));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(storeRepository.saveAndFlush(store)).thenReturn(store);

        // when
        storeService.deleteStore(storeId);
        // then
        Assertions.assertTrue(store.getIsDeleted());
        verify(storeRepository, times(1)).saveAndFlush(store);
    }

}
