package com.sparta.twotwo.store.repository;

import com.sparta.twotwo.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    Page<Store> findStoresByCategoryId(UUID categoryId, Pageable pageable);

}
