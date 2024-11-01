package com.eleven.hotel.domain.model.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer>{

    Collection<Room> findRoomsByHotelId(Integer hotelId);

    Optional<Room> findByHotelIdAndId(@Param("hotelId") Integer hotelId, @Param("Id") Integer roomId);
}
