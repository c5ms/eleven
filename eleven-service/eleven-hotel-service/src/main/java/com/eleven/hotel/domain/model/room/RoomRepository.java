package com.eleven.hotel.domain.model.room;

import com.eleven.core.data.DomainRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface RoomRepository extends DomainRepository<Room, String> {

    @Query("select * from room where hotel_id=:hotelId  order by create_at desc")
    Collection<Room> getRoomsByHotelId(@Param("hotelId") String hotelId);

    @Query("select * from room where id=:roomId and hotel_id=:hotelId")
    Optional<Room> find(String hotelId, String roomId);
}
