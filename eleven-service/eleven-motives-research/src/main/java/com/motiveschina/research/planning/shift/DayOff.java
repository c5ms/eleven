package com.motiveschina.research.planning.shift;

import lombok.Data;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.LocalDate;

@Data
public class DayOff {

    @PlanningVariable
    private Employee employee;

    private LocalDate date;

    public DayOff() {
    }

    public DayOff(Employee employee, LocalDate date) {
        this.employee = employee;
        this.date = date;
    }


}
