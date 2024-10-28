package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.NoEntityException;

public class PlanNotFoundException extends NoEntityException {

    public PlanNotFoundException(String hotelId, String roomId) {
        super(String.format("not found plan with id %s in hotel %s", roomId, hotelId));
    }
}
