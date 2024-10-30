package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.NoRequiredEntityException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends CrudRepository<Plan, String> {

    Optional<Plan> findByHotelIdAndPlanId(@Param("hotelId") String hotelId, @Param("planId") String planId);

    default Plan require(String hotelId, String planId) {
        return findByHotelIdAndPlanId(hotelId, planId).orElseThrow(NoRequiredEntityException::new);
    }
}
