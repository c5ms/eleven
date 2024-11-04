package com.eleven.booking.api.application.error;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface BookingErrors {

    DomainError DEMO = SimpleDomainError.of("demo", "this is a demo error");


}
