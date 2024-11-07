package com.eleven.hotel.domain.model.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager {
    private final InventoryRepository inventoryRepository;

    public void mergeInventory(Plan plan) {
        var existingInventories = inventoryRepository.findByPlanKey(plan.getKey());
        var merger = InventoryMerge.of(plan, existingInventories);

        // remove inventory which has never been used and is invalid now.
        inventoryRepository.deleteAllInBatch(merger.removes());

        // update invalid status
        inventoryRepository.updateAllAndFlush(merger.invalids());

        // add new inventories
        inventoryRepository.persistAll(merger.adds());
    }

}
