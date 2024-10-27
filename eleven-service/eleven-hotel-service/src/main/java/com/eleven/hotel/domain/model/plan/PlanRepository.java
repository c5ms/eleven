package com.eleven.hotel.domain.model.plan;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends CrudRepository<Plan, String> {

    @Query("select * from plan where  hotel_id=:hotelId and plan_id=:planId")
    Optional<Plan> find(@Param("hotelId") String hotelId, @Param("planId") String planId);
}
