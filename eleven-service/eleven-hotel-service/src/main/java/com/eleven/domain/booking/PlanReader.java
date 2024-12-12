package com.eleven.domain.booking;


import java.util.Optional;

public interface PlanReader {

    Optional<Plan> readPlan(Long hotelId, Long planId, Long roomId);

}
