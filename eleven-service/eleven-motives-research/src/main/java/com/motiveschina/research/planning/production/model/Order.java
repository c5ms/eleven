package com.motiveschina.research.planning.production.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

    private String name;
    private Product product;
    private int quantity;
    private int deadline;

    public Order(String name, Product product, int quantity,int deadline) {
        this.name = name;
        this.product = product;
        this.quantity = quantity;
        this.deadline=deadline;
    }

    public boolean isApplicable(Machine machine) {
        return product.isApplicable(machine);
    }

    public int getTotalHours() {
        return this.product.getCostHours() * this.quantity;
    }

}
