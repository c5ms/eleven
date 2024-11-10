package com.eleven.hotel.domain.model.plan;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class InventoryCreator {
    private final Product product;

    public static InventoryCreator of(Product product) {
        return new InventoryCreator(product);
    }

    public Inventory create(LocalDate date) {
        return Inventory.of(product.getProductKey(), date, product.getStockAmount());
    }


}
