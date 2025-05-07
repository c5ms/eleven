package com.motiveschina.research.planning.production.model;

import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Data
@NoArgsConstructor
@PlanningEntity
public class ProductAssignment {

    @PlanningId
    private String id;

    private Order order;

    @PlanningVariable
    private Machine machine;

    @PlanningVariable(valueRangeProviderRefs = "timeslots")
    private Integer startTime;

    public ProductAssignment(String id, Order order) {
        this.id = id;
        this.order = order;
    }

    public boolean isMachineApplicable() {
        return this.order.isApplicable(machine);
    }

    public boolean isTheSameMachine(ProductAssignment that) {
        return Objects.equals(that.getMachine(), this.getMachine());
    }

    public boolean hasOverlap(ProductAssignment that) {
        var thisStartTime = this.getStartTime();
        var thisEndTime = this.getEndTime();
        var thatStartTime = that.getStartTime();
        var thatEndTime = that.getEndTime();
        return !(thisEndTime < thatStartTime || thatEndTime < thisStartTime);
    }

    public boolean isDelay() {
        return this.order.getDeadline() < this.getEndTime();
    }

    public int getEndTime() {
        return this.getStartTime() + this.getDuration();
    }

    public int getDuration() {
        return this.order.getTotalHours();
    }

    public int getDays() {
        var days = (double) this.order.getTotalHours() / (double) this.machine.getCapacityHours();
        return (int) Math.round(days);
    }

    public Product getProduct() {
        return order.getProduct();
    }

    public int getCost() {
        return this.machine.getCost() * this.getDuration();
    }

    public String describe() {
        return String.format("%s(%s) works on %s from %03d-%03d, cost:%03d, days %02d is-delay %s\t is-applicable %s",
            order.getName(),
            order.getProduct().getName(),
            machine.getName(),
            getStartTime(),
            getEndTime(),
            getCost(),
            getDays(),
            isDelay(),
            isMachineApplicable());
    }
}
