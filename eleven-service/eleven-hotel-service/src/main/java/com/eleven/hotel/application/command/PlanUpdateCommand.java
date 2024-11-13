package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.plan.PlanPatch;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class PlanUpdateCommand {

    private PlanPatch patch;

    @Builder.Default
    private Set<Long> rooms = new HashSet<>();
}
