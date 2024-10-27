package com.eleven.hotel.domain.model.hotel;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface HotelRepository extends CrudRepository<Hotel, String> {

    @Query("select * from hotel where hotel_name=:name")
    Collection<Hotel> getHotelByName(@Param("name") String name);

    Optional<Hotel> findByHotelId(String hotelId);

    default Hotel require(String hotelId) throws HotelNotFoundException {
        return findByHotelId(hotelId).orElseThrow(() -> new HotelNotFoundException(hotelId));
    }

}
