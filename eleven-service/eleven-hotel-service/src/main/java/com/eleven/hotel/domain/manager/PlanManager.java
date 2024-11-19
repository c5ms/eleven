package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanInventoryMigration;
import com.eleven.hotel.domain.model.plan.PlanInventoryRepository;
import com.eleven.hotel.domain.model.plan.PlanValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanValidator> planValidators;
    private final PlanInventoryRepository planInventoryRepository;

    public void validate(Plan plan) {
        for (PlanValidator validator : planValidators) {
            validator.validate(plan);
        }
    }

    public void initializeInventoryFor(Plan plan) {
        var existingInventories = planInventoryRepository.findByPlanKey(plan.toKey());
        var merger = PlanInventoryMigration.of(plan, existingInventories);

        // remove inventory which has never been used and is invalid now.
        planInventoryRepository.deleteAllInBatch(merger.removes());

        // update invalid status
        planInventoryRepository.saveAllAndFlush(merger.updates());

        // add new inventories
        planInventoryRepository.saveAllAndFlush(merger.adds());
    }

}
