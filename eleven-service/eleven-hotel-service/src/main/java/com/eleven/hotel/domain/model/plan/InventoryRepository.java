package com.eleven.hotel.domain.model.plan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryKey> {

    Optional<Inventory> findByInventoryKey(InventoryKey key);

    List<Inventory> findByPlanKey(PlanKey key);

    void deleteByPlanKey(PlanKey planKey);
}
