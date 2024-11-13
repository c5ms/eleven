package com.eleven.hotel.application.service.authorize;

import com.eleven.core.application.authorize.TypedObjectAuthorizer;
import com.eleven.hotel.domain.model.plan.Plan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanAuthorizer extends TypedObjectAuthorizer<Plan> {

    @Override
    protected boolean checkIsReadable(Plan hotel) {
        return true;
    }

    @Override
    protected boolean checkIsWritable(Plan hotel) {
        return true;
    }
}
