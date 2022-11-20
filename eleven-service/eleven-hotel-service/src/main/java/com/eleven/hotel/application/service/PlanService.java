package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.domain.model.plan.Plan;

public interface PlanService {

    Plan createPlan(PlanCreateCommand command);

    void addRoom(PlanAddRoomCommand command);

}
