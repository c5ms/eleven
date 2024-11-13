package com.eleven.booking.application.service;

import com.eleven.booking.domain.model.booking.Plan;

import java.util.Optional;

public interface PlanReader {

    Optional<Plan> readPlan(Long hotelId, Long planId, Long roomId);

}
