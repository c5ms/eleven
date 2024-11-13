package com.eleven.hotel.domain.model.inventory;

import com.eleven.hotel.domain.model.plan.PlanKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryKey> {

    Optional<Inventory> findByInventoryKey(InventoryKey key);

    @Query("from Inventory where inventoryKey.hotelId=:#{#key.hotelId} and inventoryKey.planId=:#{#key.planId} ")
    List<Inventory> findByPlanKey(@Param("key") PlanKey key);

    @Modifying
    @Query("delete Inventory where inventoryKey.hotelId=:#{#key.hotelId} and inventoryKey.planId=:#{#key.planId} ")
    void deleteByPlanKey(@Param("key")PlanKey key);
}
