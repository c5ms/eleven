package com.eleven.hotel.domain.model.booking;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanInfoRepository extends CrudRepository<PlanInfo, Long> {


    Optional<PlanInfo> findByPlanId(Long planId);
}

