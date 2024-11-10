package com.eleven.hotel.domain.model.hotel;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoomRepository extends BaseJpaRepository<Room, Long> {

    Collection<Room> findRoomsByHotelId(Long hotelId);

    Optional<Room> findByHotelIdAndRoomId(@Param("hotelId") Long hotelId, @Param("roomId") Long roomId);

    Collection<Room> findByHotelIdAndRoomIdIn(@Param("hotelId") Long hotelId,@Param("roomId")  Set<Long> roomId);
}
