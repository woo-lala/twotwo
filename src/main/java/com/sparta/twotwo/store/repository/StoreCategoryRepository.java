package com.sparta.twotwo.store.repository;

import com.sparta.twotwo.store.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, UUID> {

}
