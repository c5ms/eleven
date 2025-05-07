package com.motiveschina.research.planning.shift;

import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Data
@PlanningSolution
public class ShiftSchedule {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<LocalDate> dates;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Employee> employees;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<IsWorkingDay> isWorkingDays = Arrays.asList(IsWorkingDay.FALSE, IsWorkingDay.TRUE);

    @ProblemFactCollectionProperty
    private List<DayOff> dayOffs;

    @PlanningEntityCollectionProperty
    private List<ShiftAssignment> assignments;

    @PlanningScore
    private HardSoftScore score;

    public ShiftSchedule() {
    }

    public ShiftSchedule(List<LocalDate> dates, List<Employee> employees, List<ShiftAssignment> assignments, List<DayOff> dayOffs) {
        this.dates = dates;
        this.employees = employees;
        this.assignments = assignments;
        this.dayOffs = dayOffs;
    }
}
