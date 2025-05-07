package com.motiveschina.research.planning.production;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import com.motiveschina.research.planning.production.model.Machine;
import com.motiveschina.research.planning.production.model.Order;
import com.motiveschina.research.planning.production.model.Product;
import com.motiveschina.research.planning.production.model.ProductAssignment;
import com.motiveschina.research.planning.production.model.ProductSchedule;
import com.motiveschina.research.planning.production.solver.ProductScheduleConstraintProvider;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;

public class ProductScheduleApp {

    public static void main(String[] args) {
        var solverFactory = SolverFactory.<ProductSchedule>create(new SolverConfig()
            .withSolutionClass(ProductSchedule.class)
            .withEntityClasses(ProductAssignment.class)
            .withConstraintProviderClass(ProductScheduleConstraintProvider.class)
            .withTerminationConfig(new TerminationConfig()
                .withSpentLimit(Duration.ofSeconds(5))
                .withBestScoreFeasible(true)
            )
        );

        var machine1 = new Machine("M-1", 8, 5);
        var machine2 = new Machine("M-2", 6, 6);
        var machine3 = new Machine("M-3", 7, 8);

        var produceA = new Product("A", 3, List.of(machine1, machine2));
        var produceB = new Product("B", 2, List.of(machine2));
        var produceC = new Product("C", 4, List.of(machine1, machine3));

        var order1 = new Order("Order-1", produceA, 5, 50);
        var order2 = new Order("Order-2", produceB, 4, 50);
        var order3 = new Order("Order-3", produceC, 6, 50);
        var order4 = new Order("Order-4", produceA, 8, 50);
        var order5 = new Order("Order-5", produceB, 2, 50);
        var order6 = new Order("Order-6", produceC, 2, 50);

        var machines = Arrays.asList(
            machine1, machine2, machine3
        );
        var products = Arrays.asList(
            produceA, produceB, produceC
        );
        var orders = Arrays.asList(
            order1, order2, order3, order4, order5, order6
        );

        var assignments = orders.stream()
            .map(order -> new ProductAssignment(order.getName(), order))
            .toList();

        int totalHours = orders.stream()
            .map(Order::getTotalHours)
            .reduce(Integer::sum)
            .get();
        var timeslots = new ArrayList<Integer>();
        for (int i = 0; i < totalHours; i++) {
            timeslots.add(i);
        }

        var problem = new ProductSchedule(machines, products, orders, assignments, timeslots);
        var solver = solverFactory.buildSolver();
        var solution = solver.solve(problem);

        System.out.println(solution.getScore());
        System.out.println(solution.getTotalCostFee());
        var assignmentsResult = solution.getAssignments().stream()
            .sorted(Comparator.comparing(o -> o.getProduct().getName()))
            .toList();
        for (ProductAssignment assignment : assignmentsResult) {
            System.out.println(assignment.describe());
        }
    }


}
