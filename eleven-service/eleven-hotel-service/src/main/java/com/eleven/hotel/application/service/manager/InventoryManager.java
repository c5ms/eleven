package com.eleven.hotel.application.service.manager;

import com.eleven.hotel.domain.model.plan.InventoryMerge;
import com.eleven.hotel.domain.model.plan.InventoryRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager {
    private final InventoryRepository inventoryRepository;

    public void initialize(Plan plan) {
        var existingInventories = inventoryRepository.findByPlanKey(plan.getPlanKey());
        var merger = InventoryMerge.of(plan, existingInventories);

        // remove inventory which has never been used and is invalid now.
        inventoryRepository.deleteAllInBatch(merger.removes());

        // update invalid status
        inventoryRepository.updateAllAndFlush(merger.updates());

        // add new inventories
        inventoryRepository.persistAllAndFlush(merger.adds());
    }

}
