package com.sparta.twotwo.store.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
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

import java.util.Optional;
import java.util.UUID;

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
    public Store saveStore(final StoreCreateRequest request) {

        Member member = getMemberOrException(request.getMemberId());
        final Address address = addressService.saveAddress(request.getAddress());
        StoreCategory category = getCategoryOrException(request.getCategoryId());
        validateStoreName(request.getName());

        Store newStore = Store.builder()
                .name(request.getName())
                .address(address)
                .minOrderPrice(request.getMinOrderPrice())
                .operationClosedAt(request.getOperationClosedAt())
                .operationStartedAt(request.getOperationStartedAt())
                .category(category)
                .imageUrl(request.getImageUrl())
                .member(member)
                .build();

        return storeRepository.save(newStore);

    }

    @Transactional
    public Store updateStore(UUID storeId, StoreUpdateRequest request) {

        //멤버 가져오기
        Member member = getMemberOrException(request.getMemberId());

        //가게 존재하는지 확인
        Store store = getStoreOrException(storeId);

        validateStoreName(request.getName());

        Optional.ofNullable(request.getCategoryId()).ifPresent(categoryId -> {
                    StoreCategory category = getCategoryOrException(categoryId);
                    store.setCategory(category);
                }
        );

        Optional.ofNullable(request.getAddress()).ifPresent(requestAddress -> {
                    Address updatedAddress = addressService.updateAddress(requestAddress);
                    store.setAddress(updatedAddress);
                }
        );

        Optional.ofNullable(request.getName()).ifPresent(store::setName);
        Optional.ofNullable(request.getMinOrderPrice()).ifPresent(store::setMinOrderPrice);
        Optional.ofNullable(request.getOperationStartedAt()).ifPresent(store::setOperationStartedAt);
        Optional.ofNullable(request.getOperationClosedAt()).ifPresent(store::setOperationClosedAt);
        Optional.ofNullable(request.getImageUrl()).ifPresent(store::setImageUrl);

        return storeRepository.save(store);
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


