package com.eleven.hotel.domain.model.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanInventoryRepository extends JpaRepository<PlanInventory, PlanInventoryKey> {

    Optional<PlanInventory> findByKey(PlanInventoryKey key);

    @Query("from PlanInventory where key.hotelId=:#{#key.hotelId} and key.planId=:#{#key.planId} ")
    List<PlanInventory> findByPlanKey(@Param("key") PlanKey key);

    @Modifying
    @Query("delete PlanInventory where key.hotelId=:#{#key.hotelId} and key.planId=:#{#key.planId} ")
    void deleteByPlanKey(@Param("key") PlanKey key);
}
