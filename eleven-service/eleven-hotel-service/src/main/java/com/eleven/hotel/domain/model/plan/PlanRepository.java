package com.eleven.hotel.domain.model.plan;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends BaseJpaRepository<Plan, Integer>, JpaSpecificationExecutor<Plan> {

    Optional<Plan> findByHotelIdAndPlanId(@Param("hotelId") Integer hotelId, @Param("planId") Integer planId);

}
