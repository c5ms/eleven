package com.eleven.hotel.domain.model.inventory;

import com.eleven.hotel.domain.model.plan.Plan;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryMerger {

    private final Plan plan;
    private final List<Inventory> existingInventories;
    private final List<Inventory> currentInventories;

    private InventoryMerger(Plan plan, List<Inventory> existingInventories) {
        this.plan = plan;
        this.existingInventories = existingInventories;
        this.currentInventories = plan.createInventories();
    }

    public static InventoryMerger of(Plan plan, List<Inventory> existingInventories) {
        return new InventoryMerger(plan, existingInventories);
    }

    public Collection<Inventory> removes() {
        return existingInventories.stream()
                .filter(inventory -> !inventory.hasBeenBooked())
                .filter(inventory -> !plan.isApplicable(inventory))
                .collect(Collectors.toSet());
    }

    public Collection<Inventory> updates() {
        return existingInventories.stream()
                .filter(Inventory::hasBeenBooked)
                .peek(inventory -> {
                    var isValid = plan.isApplicable(inventory);
                    inventory.setIsValid(isValid);
                })
                .collect(Collectors.toSet());
    }

    public Collection<Inventory> adds() {
        var adding = new HashMap<InventoryKey, Inventory>();
        currentInventories.forEach(inventory -> adding.put(inventory.getInventoryKey(), inventory));
        existingInventories.stream().map(Inventory::getInventoryKey).forEach(adding::remove);

        return adding.values()
                .stream()
                .filter(plan::isApplicable)
                .collect(Collectors.toList());
    }

}
