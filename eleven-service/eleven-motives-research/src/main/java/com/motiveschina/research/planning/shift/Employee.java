package com.motiveschina.research.planning.shift;

import lombok.Data;

@Data
public class Employee {

    private String name;

    public Employee(String name) {
        this.name = name;
    }
}
