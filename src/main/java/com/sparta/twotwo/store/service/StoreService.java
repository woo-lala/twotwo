package com.sparta.twotwo.store.service;

import com.sparta.twotwo.address.service.AddressService;
import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.product.service.ProductService;
import com.sparta.twotwo.store.dto.request.StoreSearchRequestDto;
import com.sparta.twotwo.address.entity.Address;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.sparta.twotwo.auth.util.SecurityUtil.getMemberIdFromSecurityContext;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final SecurityUtil securityUtil;
    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final MemberRepository memberRepository;
    private final AddressService addressService;
    private final ProductService productService;

    public Page<Store> searchStores(StoreSearchRequestDto search, Pageable pageable) {
        return storeRepository.search(search, pageable);
    }

    public Page<Store> getAllStores(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public Store getStoreDetails(UUID storeId) {
        return getStoreOrException(storeId);
    }

    public Page<Store> getStoresByCategory(UUID categoryId, Pageable pageable) {
        Optional<StoreCategory> category = storeCategoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND);
        }
        return storeRepository.findStoresByCategoryId(categoryId, pageable);
    }

    @Transactional
    public Store saveStore(Store reqStore) {

        Member member = getMemberOrException(reqStore.getMember().getMember_id());

        final Address address = addressService.saveAddress(reqStore.getAddress());
        StoreCategory category = getCategoryOrException(reqStore.getCategory().getId());
        validateStoreName(reqStore.getName());

        Store newStore = Store.builder()
                .name(reqStore.getName())
                .address(address)
                .minOrderPrice(reqStore.getMinOrderPrice())
                .operationClosedAt(reqStore.getOperationClosedAt())
                .operationStartedAt(reqStore.getOperationStartedAt())
                .category(category)
                .imageUrl(reqStore.getImageUrl())
                .member(member)
                .build();

        Long creatorId = getMemberIdFromSecurityContext();
        newStore.setCreatedBy(creatorId);

        return storeRepository.save(newStore);
    }

    @Transactional
    public Store updateStore(UUID storeId, Store reqStore) {

        Long modifierId = securityUtil.getMemberId();
        Member modifier = getMemberOrException(modifierId);
        Store store = getStoreOrException(storeId);

        validateMember(modifier, store);

        Optional.ofNullable(reqStore.getName()).ifPresent(storeName -> {
                    if (storeName.equals(store.getName())) {
                        throw new TwotwoApplicationException(ErrorCode.NO_STORE_NAME_CHANGES);
                    }
                    validateStoreName(reqStore.getName());
                    store.updateName(storeName);
                }
        );

        Optional.ofNullable(reqStore.getCategory()).ifPresent(storeCategory -> {
                    StoreCategory category = getCategoryOrException(storeCategory.getId());
                    store.updateCategory(category);
                }
        );

        Optional.ofNullable(reqStore.getAddress()).ifPresent(requestAddress -> {
                    Address updatedAddress = addressService.updateAddress(store.getAddress(), requestAddress);
                    store.updateAddress(updatedAddress);
                }
        );

        Optional.ofNullable(reqStore.getMember()).ifPresent(member -> {
            Member owner = getMemberOrException(member.getMember_id());
            store.updateMember(owner);
        });

        Optional.ofNullable(reqStore.getImageUrl()).ifPresent(store::updateImageUrl);
        Optional.ofNullable(reqStore.getMinOrderPrice()).ifPresent(store::updateMinOrderPrice);
        Optional.ofNullable(reqStore.getOperationStartedAt()).ifPresent(store::updateOperationStartedAt);
        Optional.ofNullable(reqStore.getOperationClosedAt()).ifPresent(store::updateOperationClosedAt);

        store.setUpdatedBy(modifierId);

        return storeRepository.save(store);

    }

    @Transactional
    public Store deleteStore(UUID storeId) {

        Long deleterId = securityUtil.getMemberId();
        Store store = getStoreOrException(storeId);
        Member deleter = getMemberOrException(deleterId);

        validateMember(deleter, store);

        store.setIsDeleted(Boolean.TRUE);
        store.setDeletedAt(LocalDateTime.now());
        store.setDeletedBy(deleterId);
        store.setIsDeleted(Boolean.TRUE);

        List<Product> products = store.getProducts();
        if (products != null) {
            for (Product product : products) {
                productService.deleteProduct(product.getId());
            }
        }
        return storeRepository.saveAndFlush(store);
    }

    private void validateStoreName(String storeName) {
        storeRepository.findByName(storeName).ifPresent(it -> {
            throw new TwotwoApplicationException(ErrorCode.STORE_NAME_EXIST);
        });
    }

    private void validateMember(Member member, Store store) {
        if (!member.getRoles().contains("MASTER") && !member.getRoles().contains("MANAGER") && !member.getMember_id().equals(store.getMember().getMember_id())) {
            throw new TwotwoApplicationException(ErrorCode.UNAUTHORIZED);
        }
    }

    private StoreCategory getCategoryOrException(UUID categoryId) {
        return storeCategoryRepository.findById(categoryId).orElseThrow(
                () -> new TwotwoApplicationException(ErrorCode.NOT_FOUND)
        );
    }

    private Member getMemberOrException(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND)
        );
    }

    private Store getStoreOrException(UUID storeId) {
        return storeRepository.findById(storeId).orElseThrow(
                () -> new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND)
        );
    }

}


