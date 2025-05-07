package com.motiveschina.hotel.features.room;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    @Query("from Room where hotelId=:hotelId order by roomId desc")
    Collection<Room> findByHotelId(Long hotelId);

    @Query("from Room where hotelId=:#{#key.hotelId} and roomId=:#{#key.roomId} ")
    Optional<Room> findByRoomKey(@Param("key") RoomKey key);
}
