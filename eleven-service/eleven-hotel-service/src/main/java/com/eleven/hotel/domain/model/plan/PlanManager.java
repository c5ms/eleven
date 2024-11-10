package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.errors.HotelErrors;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanValidator> planValidators;

    public void validate(Plan plan) {
        for (PlanValidator validator : planValidators) {
            validator.validate(plan);
        }
    }


}
