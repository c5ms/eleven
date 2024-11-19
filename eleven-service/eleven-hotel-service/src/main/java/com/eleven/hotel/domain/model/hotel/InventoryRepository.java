package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.DateRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryKey> {

    @Query("from Inventory where key.hotelId=:#{#roomKey.hotelId} and key.roomId=:#{#roomKey.roomId} ")
    Collection<Inventory> findByRoomKey(@Param("roomKey") RoomKey key);

    @Modifying
    @Query("delete Inventory where key.hotelId=:#{#roomKey.hotelId} and key.roomId=:#{#roomKey.roomId} ")
    long deleteByRoomKey(@Param("roomKey") RoomKey key);

}
