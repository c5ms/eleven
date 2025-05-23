package com.motiveschina.hotel.features.plan;

import com.eleven.framework.authorize.TypedObjectAuthorizer;
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
