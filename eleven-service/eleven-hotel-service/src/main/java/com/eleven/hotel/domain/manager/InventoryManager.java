package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.inventory.InventoryMerger;
import com.eleven.hotel.domain.model.inventory.InventoryRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager {

    private final InventoryRepository inventoryRepository;

    public void initializeInventoryFor(Plan plan) {
        var existingInventories = inventoryRepository.findByPlanKey(plan.getPlanKey());
        var merger = InventoryMerger.of(plan, existingInventories);

        // remove inventory which has never been used and is invalid now.
        inventoryRepository.deleteAllInBatch(merger.removes());

        // update invalid status
        inventoryRepository.saveAllAndFlush(merger.updates());

        // add new inventories
        inventoryRepository.saveAllAndFlush(merger.adds());
    }

}
