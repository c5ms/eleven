package com.eleven.hotel.domain.model.inventory;

import com.eleven.hotel.domain.model.plan.Product;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class InventoryFactory {
    private final Product product;

    public static InventoryFactory of(Product product) {
        return new InventoryFactory(product);
    }

    public Inventory create(LocalDate date) {
        return Inventory.of(product.getProductKey(), date, product.getStock());
    }

}
