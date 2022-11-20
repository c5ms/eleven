package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.DomainRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRepository extends DomainRepository<Hotel, String> {

    @Query("select * from hotel where hotel_name=:name")
    Optional<Hotel> getHotelByName(@Param("name") String name);
}
