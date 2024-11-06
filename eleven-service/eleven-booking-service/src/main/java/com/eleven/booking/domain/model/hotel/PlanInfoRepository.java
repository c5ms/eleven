package com.eleven.booking.domain.model.hotel;

import com.eleven.booking.domain.core.ReadonlyRepository;

import java.util.Optional;

public interface PlanInfoRepository extends ReadonlyRepository<PlanInfo, Long> {

    Optional<PlanInfo> findByPlanId(Long planId);
}

