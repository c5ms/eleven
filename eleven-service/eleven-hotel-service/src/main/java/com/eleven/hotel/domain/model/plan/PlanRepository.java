package com.eleven.hotel.domain.model.plan;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends BaseJpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {

    Optional<Plan> findByHotelIdAndPlanId(@Param("hotelId") Long hotelId, @Param("planId") Long planId);

}
