package com.sparta.twotwo.store.repository;

import com.sparta.twotwo.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.reviews")
    List<Store> findStoresWithReviews();

    Page<Store> findStoresByCategoryId(UUID categoryId, Pageable pageable);

    Optional<Store> findByName(String name);

}
