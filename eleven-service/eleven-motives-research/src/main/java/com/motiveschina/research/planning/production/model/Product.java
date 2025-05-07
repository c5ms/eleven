package com.motiveschina.research.planning.production.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {

    private String name;

    private int costHours;

    private  List<Machine> applicableMachines;

    public Product(String name, int costHours, List<Machine> applicableMachines) {
        this.name = name;
        this.costHours = costHours;
        this.applicableMachines=applicableMachines;
    }

    public boolean isApplicable(Machine machine) {
        return applicableMachines.contains(machine);
    }
}
