package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface HotelRepository extends CrudRepository<Hotel, String> {

    @Query("select * from hotel where hotel_name=:name")
    Collection<Hotel> getHotelByName(@Param("name") String name);

    Optional<Hotel> findByHotelId(String hotelId);

    default Hotel require(String hotelId) {
        return findByHotelId(hotelId).orElseThrow(NoEntityException::new);
    }

}
