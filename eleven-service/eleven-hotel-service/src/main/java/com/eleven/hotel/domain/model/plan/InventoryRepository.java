package com.eleven.hotel.domain.model.plan;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends BaseJpaRepository<Inventory, InventoryKey> {

    Optional<Inventory> findByInventoryKey(InventoryKey key);

    List<Inventory> findByPlanKey(PlanKey key);

    void deleteByPlanKey(PlanKey planKey);
}
