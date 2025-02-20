package com.sparta.twotwo.store.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.store.dto.request.AddressRequest;
import com.sparta.twotwo.store.dto.request.AddressUpdateRequest;
import com.sparta.twotwo.store.dto.request.StoreCreateRequest;
import com.sparta.twotwo.store.dto.request.StoreUpdateRequest;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import com.sparta.twotwo.store.repository.StoreCategoryRepository;
import com.sparta.twotwo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static com.sparta.twotwo.auth.util.SecurityUtil.getMemberIdFromSecurityContext;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final MemberRepository memberRepository;
    private final AddressService addressService;

    public Page<Store> getAllStores(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public Optional<Store> getStoreDetails(UUID storeId) {
        return storeRepository.findById(storeId);
    }

    public Page<Store> getStoresByCategory(UUID categoryId, Pageable pageable) {
        Optional<StoreCategory> category = storeCategoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND);
        }
        return storeRepository.findStoresByCategoryId(categoryId, pageable);
    }

    @Transactional
    public Store saveStore(
            String reqName,
            Long reqMemberId,
            AddressRequest reqAddress,
            UUID reqCategoryId,
            String reqImageUrl,
            Long reqMinOrderPrice,
            LocalTime reqOperationStartedAt,
            LocalTime reqOperationClosedAt
    ) {

        Member member = getMemberOrException(reqMemberId);
        final Address address = addressService.saveAddress(reqAddress);
        StoreCategory category = getCategoryOrException(reqCategoryId);
        validateStoreName(reqName);

        Store newStore = Store.builder()
                .name(reqName)
                .address(address)
                .minOrderPrice(reqMinOrderPrice)
                .operationClosedAt(reqOperationClosedAt)
                .operationStartedAt(reqOperationStartedAt)
                .category(category)
                .imageUrl(reqImageUrl)
                .member(member)
                .build();

        Long creatorId = getMemberIdFromSecurityContext();
        newStore.setCreatedBy(creatorId);

        return storeRepository.save(newStore);

    }

    @Transactional
    public Store updateStore(
            UUID storeId,
            String reqName,
            Long reqMemberId,
            AddressUpdateRequest reqAddress,
            UUID reqCategoryId,
            String reqImageUrl,
            Long reqMinOrderPrice,
            LocalTime reqOperationStartedAt,
            LocalTime reqOperationClosedAt
    ) {

        //가게 존재하는지 확인
        Store store = getStoreOrException(storeId);

        validateStoreName(reqName);

        Optional.ofNullable(reqCategoryId).ifPresent(categoryId -> {
                    StoreCategory category = getCategoryOrException(categoryId);
                    store.updateCategory(category);
                }
        );

        //가게 주인 변경
        Optional.ofNullable(reqAddress).ifPresent(requestAddress -> {
                    Address updatedAddress = addressService.updateAddress(requestAddress);
                    store.updateAddress(updatedAddress);
                }
        );

        Optional.ofNullable(reqMemberId).ifPresent(memberId -> {
            Member owner = getMemberOrException(memberId);
            store.updateMember(owner);
        });

        Optional.ofNullable(reqName).ifPresent(store::updateName);
        Optional.ofNullable(reqImageUrl).ifPresent(store::updateImageUrl);
        Optional.ofNullable(reqMinOrderPrice).ifPresent(store::updateMinOrderPrice);
        Optional.ofNullable(reqOperationStartedAt).ifPresent(store::updateOperationStartedAt);
        Optional.ofNullable(reqOperationClosedAt).ifPresent(store::updateOperationClosedAt);


        //변경하는 사용자 id 가져오기
        Long modifierId = getMemberIdFromSecurityContext();
        store.setUpdatedBy(modifierId);

        return storeRepository.save(store);
    }

    @Transactional
    public Store deleteStore(UUID storeId) {
        //가게 존재하는지 확인
        Store store = getStoreOrException(storeId);

        Long deleterId = getMemberIdFromSecurityContext();

        store.setIsDeleted(Boolean.TRUE);
        store.setDeletedAt(LocalDateTime.now());
        store.setDeletedBy(deleterId);

        return storeRepository.saveAndFlush(store);
    }

    private void validateStoreName(String storeName) {
        storeRepository.findByName(storeName).ifPresent(it -> {
            throw new TwotwoApplicationException(ErrorCode.STORE_NAME_EXIST);
        });
    }

    private StoreCategory getCategoryOrException(UUID categoryId) {
        return storeCategoryRepository.findById(categoryId).orElseThrow(() -> new TwotwoApplicationException(ErrorCode.NOT_FOUND));
    }

    private Member getMemberOrException(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Store getStoreOrException(UUID storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND));
    }


}


