package com.eleven.hotel.domain.model.plan;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PlanInventoryMigration {

    private final Plan plan;
    private final List<PlanInventory> existingInventories;
    private final List<PlanInventory> currentInventories;

    private PlanInventoryMigration(Plan plan, List<PlanInventory> existingInventories) {
        this.plan = plan;
        this.existingInventories = existingInventories;
        this.currentInventories = plan.createInventories();
    }

    public static PlanInventoryMigration of(Plan plan, List<PlanInventory> existingInventories) {
        return new PlanInventoryMigration(plan, existingInventories);
    }

    public Collection<PlanInventory> removes() {
        return existingInventories.stream()
                .filter(inventory -> !inventory.hasBeenBooked())
                .filter(inventory -> !plan.isApplicable(inventory))
                .collect(Collectors.toSet());
    }

    public Collection<PlanInventory> updates() {
        return existingInventories.stream()
                .filter(PlanInventory::hasBeenBooked)
                .peek(inventory -> {
                    var isValid = plan.isApplicable(inventory);
                    inventory.setIsValid(isValid);
                })
                .collect(Collectors.toSet());
    }

    public Collection<PlanInventory> adds() {
        var adding = new HashMap<PlanInventoryKey, PlanInventory>();
        currentInventories.forEach(inventory -> adding.put(inventory.getKey(), inventory));
        existingInventories.stream().map(PlanInventory::getKey).forEach(adding::remove);

        return adding.values()
                .stream()
                .filter(plan::isApplicable)
                .collect(Collectors.toList());
    }

}
