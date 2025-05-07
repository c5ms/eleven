package com.motiveschina.research.planning.production.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@Data
@PlanningSolution
@NoArgsConstructor
public class ProductSchedule {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Machine> machines;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Order> orders;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Product> products;

    @ValueRangeProvider(id = "timeslots")
    @ProblemFactCollectionProperty
    private List<Integer> timeslot;

    @PlanningEntityCollectionProperty
    private List<ProductAssignment> assignments;

    @PlanningScore
    private HardSoftScore score;

    public ProductSchedule(List<Machine> machines,
                           List<Product> products,
                           List<Order> orders,
                           List<ProductAssignment> assignments,
                           List<Integer> timeslot) {
        this.machines = machines;
        this.products = products;
        this.orders = orders;
        this.assignments = assignments;
        this.timeslot = timeslot;
    }

    public int getTotalCostFee() {
        int total = 0;
        for (ProductAssignment assignment : this.assignments) {
            total += assignment.getCost();
        }
        return total;
    }
}
