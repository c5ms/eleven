package com.eleven.hotel.domain.model.plan;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

import java.util.Optional;

public interface InventoryRepository extends BaseJpaRepository<Inventory, Long> {

    Optional<Inventory> findByKey(InventoryKey key);
}
