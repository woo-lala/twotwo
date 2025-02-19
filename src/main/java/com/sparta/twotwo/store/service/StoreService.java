package com.sparta.twotwo.store.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.store.dto.request.StoreCreateRequest;
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
        //member 존재하는지 확인
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND));

        //주소 저장
        final Address address = addressService.saveAddress(request.getAddress());

        //가게 생성
        StoreCategory category = storeCategoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new TwotwoApplicationException(ErrorCode.NOT_FOUND));

        //가게 중복 이름 확인
        storeRepository.findByName(request.getName()).ifPresent( it -> {
                    throw new TwotwoApplicationException(ErrorCode.STORE_NAME_EXIST);
                });

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

}


