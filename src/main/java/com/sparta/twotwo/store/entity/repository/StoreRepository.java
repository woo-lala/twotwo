package com.sparta.twotwo.store.entity.repository;

import com.sparta.twotwo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}
