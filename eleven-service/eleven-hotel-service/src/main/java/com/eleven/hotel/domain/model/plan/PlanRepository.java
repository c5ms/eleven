package com.eleven.hotel.domain.model.plan;

import com.eleven.core.data.DomainRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface PlanRepository extends DomainRepository<Plan, String> {

    @Query("select * from plan where  hotel_id=:hotelId and id=:roomId")
    Optional<Plan> find(@Param("hotelId") String hotelId, @Param("roomId") String roomId);
}
