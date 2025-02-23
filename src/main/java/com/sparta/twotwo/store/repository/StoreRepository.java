package com.sparta.twotwo.store.repository;

import com.sparta.twotwo.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID>, StoreRepositoryQuery {

    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.reviews")
    List<Store> findStoresWithReviews();

    @Query("SELECT s.id, COALESCE(AVG(r.rating), 0.0), COUNT(r) FROM Store s LEFT JOIN s.reviews r GROUP BY s.id")
    List<Object[]> findAverageRatingsAndReviewCount();

    @Modifying
    @Query("UPDATE Store s SET s.rating = :rating, s.reviewCount = :reviewCount WHERE s.id = :storeId")
    void updateRatingAndReviewCount(UUID storeId, BigDecimal rating, Integer reviewCount);

    Page<Store> findStoresByCategoryId(UUID categoryId, Pageable pageable);

    Optional<Store> findByName(String name);

}
