package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, String> {

    @Query("select * from room where hotel_id=:hotelId  order by create_at desc")
    Collection<Room> getRoomsByHotelId(String hotelId);

    Optional<Room> findByHotelIdAndRoomId(String hotelId, String roomId);

    default Room require(String hotelId, String roomId) {
        return findByHotelIdAndRoomId(hotelId, roomId).orElseThrow(NoEntityException::new);
    }
}
