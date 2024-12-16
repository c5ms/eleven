package com.eleven.travel.domain.inventory;

import com.eleven.travel.domain.room.RoomKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface RoomInventoryRepository extends JpaRepository<RoomInventory, RoomInventoryKey> {

    @Query("from RoomInventory where key.hotelId=:#{#roomKey.hotelId} and key.roomId=:#{#roomKey.roomId} ")
    Collection<RoomInventory> findByRoomKey(@Param("roomKey") RoomKey key);

    @Modifying
    @Query("delete RoomInventory where key.hotelId=:#{#roomKey.hotelId} and key.roomId=:#{#roomKey.roomId} ")
    long deleteByRoomKey(@Param("roomKey") RoomKey key);

//    @Modifying
//    @Query("update Inventory set isValid=false where key.hotelId=:#{#roomKey.hotelId} and key.roomId=:#{#roomKey.roomId} " +
//            "and (key.date < :#{#range.start}  or key.date > :#{#range.end})")
//    void invalidOutOf(@Param("roomKey") RoomKey key, @Param("range")  DateRange range);
}
