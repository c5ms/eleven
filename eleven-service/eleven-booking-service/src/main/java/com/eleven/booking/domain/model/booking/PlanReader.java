package com.eleven.booking.domain.model.booking;

import java.util.Optional;

public interface PlanReader {

    Optional<Plan> readPlan(Long hotelId, Long planId);

}
