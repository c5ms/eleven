package com.eleven.hotel.domain.model.plan;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface InventoryRepository extends BaseJpaRepository<Inventory, Long> {

    Optional<Inventory> findByKey(InventoryKey key);

    List<Inventory> findByPlanKey(PlanKey key);

    void deleteByPlanKey(PlanKey planKey);
}
