package com.eleven.hotel.domain.model.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Collection<Room> findByHotelId(Long hotelId);

    Optional<Room> findByHotelIdAndRoomId(@Param("hotelId") Long hotelId, @Param("roomId") Long roomId);

}
