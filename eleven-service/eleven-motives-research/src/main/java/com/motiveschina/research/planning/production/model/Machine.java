package com.motiveschina.research.planning.production.model;

import lombok.Data;

@Data
public class Machine {
    private String name;
    private int capacityHours;
    private int cost;

    public Machine() {
    }

    public Machine(String name, int capacityHours, int cost) {
        this.name = name;
        this.cost = cost;
        this.capacityHours = capacityHours;
    }
}
