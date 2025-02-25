package com.sparta.twotwo.address.repository;

import com.sparta.twotwo.address.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AreaRepository extends JpaRepository<Area, UUID> {

    Optional<Area> findAreaByZipNum(String zipNum);
}
