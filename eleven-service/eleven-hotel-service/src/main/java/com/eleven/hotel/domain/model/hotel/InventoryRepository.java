package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.model.plan.PlanInventoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryKey> {

    Optional<Inventory> findByKey(PlanInventoryKey key);

    @Modifying
    @Query("delete Inventory where key.hotelId=:#{#key.hotelId} and key.roomId=:#{#key.roomId} ")
    void deleteByRoomKey(@Param("key") RoomKey key);
}
