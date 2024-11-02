package com.eleven.hotel.domain.model.hotel;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface RoomRepository extends BaseJpaRepository<Room, Integer> {

    Collection<Room> findRoomsByHotelId(Integer hotelId);

    Optional<Room> findByHotelIdAndRoomId(@Param("hotelId") Integer hotelId, @Param("roomId") Integer roomId);
}
