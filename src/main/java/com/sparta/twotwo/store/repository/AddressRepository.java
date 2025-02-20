package com.sparta.twotwo.store.repository;

import com.sparta.twotwo.store.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    Optional<Address> findByAreaIdAndRoadAddressAndDetailAddress(UUID areaId, String roadAddress, String detailAddress);

}
