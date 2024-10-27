package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.NoEntityFoundException;

public class PlanNotFoundException extends NoEntityFoundException {

    public PlanNotFoundException(String hotelId, String roomId) {
        super(String.format("not found plan with id %s in hotel %s", roomId, hotelId));
    }
}
