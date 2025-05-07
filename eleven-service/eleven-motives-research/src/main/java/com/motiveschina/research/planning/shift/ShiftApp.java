package com.motiveschina.research.planning.shift;

import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShiftApp {

    public static void main(String[] args) {
        var solverFactory = SolverFactory.<ShiftSchedule>create(new SolverConfig()
            .withSolutionClass(ShiftSchedule.class)
            .withEntityClasses(ShiftAssignment.class)
            .withConstraintProviderClass(ShiftScheduleConstraintProvider.class)
            .withTerminationSpentLimit(Duration.ofSeconds(4)
            ));

        var employees = getEmployees();
        var timeslots = getTimeslots();
        var dayOffs = getDayOffs();


        var assignments = new ArrayList<ShiftAssignment>();
        // 13 employee per day.
        int id = 1;
        for (LocalDate date : timeslots) {
            for (int i = 0; i < employees.size(); i++) {
                assignments.add(new ShiftAssignment(id++, date));
            }
        }

        var problem = new ShiftSchedule(timeslots, employees, assignments, dayOffs);
        var solver = solverFactory.buildSolver();
        var solution = solver.solve(problem);

        System.out.println(solution.getScore());
        for (ShiftAssignment assignment : solution.getAssignments()) {
            System.out.println(assignment);
        }
    }


    private static ArrayList<DayOff> getDayOffs() {
        var dayOffs = new ArrayList<DayOff>();
        dayOffs.add(new DayOff(new Employee("Alice"), LocalDate.of(2025, 4, 24)));
        return dayOffs;
    }

    private static List<LocalDate> getTimeslots() {
        var dates = new ArrayList<LocalDate>();
        dates.add(LocalDate.of(2025, 4, 24));
        dates.add(LocalDate.of(2025, 4, 25));
        dates.add(LocalDate.of(2025, 4, 26));
        return dates;
    }

    private static List<Employee> getEmployees() {
        return Arrays.asList(
            new Employee("Alice"),
            new Employee("Bob"),
            new Employee("Charlie")
        );
    }
}
