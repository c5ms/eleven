package com.eleven.hotel.domain.model.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Integer>, JpaSpecificationExecutor<Plan> {

    Optional<Plan> findByHotelIdAndId(@Param("hotelId") Integer hotelId, @Param("Id") Integer planId);

}
