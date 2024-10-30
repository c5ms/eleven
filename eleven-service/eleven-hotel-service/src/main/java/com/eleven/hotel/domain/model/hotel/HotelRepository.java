package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.NoRequiredEntityException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, String> {

    Optional<Hotel> findByHotelId(String hotelId);

    default Hotel require(String hotelId) {
        return findByHotelId(hotelId).orElseThrow(NoRequiredEntityException::new);
    }

}
