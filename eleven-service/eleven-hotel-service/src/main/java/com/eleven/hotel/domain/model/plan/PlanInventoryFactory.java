package com.eleven.hotel.domain.model.plan;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class PlanInventoryFactory {
    private final Product product;

    public static PlanInventoryFactory of(Product product) {
        return new PlanInventoryFactory(product);
    }

    public PlanInventory create(LocalDate date) {
        return PlanInventory.of(product, date);
    }

}
