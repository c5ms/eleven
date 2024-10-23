package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.DomainRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface HotelRoomRepository extends DomainRepository<HotelRoom, String> {

    @Query("select * from room where hotel_id=:hotelId and name=:name")
    Collection<HotelRoom> getRoomsByHotelIdAndName(@Param("hotelId") String hotelId, @Param("name") String roomName);

    @Query("select * from room where hotel_id=:hotelId ")
    Collection<HotelRoom> getRoomsByHotelId(@Param("hotelId") String hotelId);

    @Query("select * from room where id=:roomId and hotel_id=:hotelId")
    Optional<HotelRoom> find(String hotelId, String roomId);
}
