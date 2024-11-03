package com.eleven.hotel.domain.model.plan;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface InventoryRepository extends BaseJpaRepository<Inventory, Long> {

    Optional<Inventory> findByHotelIdAndPlanIdAndRoomId(Long hotelId, Long planId, Long roomId);

}
