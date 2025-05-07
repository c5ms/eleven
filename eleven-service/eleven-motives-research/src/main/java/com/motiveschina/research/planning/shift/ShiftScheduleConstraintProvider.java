package com.motiveschina.research.planning.shift;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;


public class ShiftScheduleConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
            hasEmployee(constraintFactory),
            noWeekend(constraintFactory),
            noDuplicateShiftsOnSameDay(constraintFactory),
            noDayOff(constraintFactory),
        };
    }

    private Constraint noDayOff(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ShiftAssignment.class)
            .filter(ShiftAssignment::isWorkingDay)
            .join(DayOff.class)
            .filter(ShiftAssignment::isDayOff)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("noDayOff");
    }

    private Constraint noDuplicateShiftsOnSameDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(ShiftAssignment.class)
            .filter(ShiftAssignment::isTheSameEmployee)
            .filter(ShiftAssignment::isTheSameDay)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Duplicate shifts on same day");
    }

    private Constraint hasEmployee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ShiftAssignment.class)
            .filter(ShiftAssignment::isNotWeekend)
            .map(ShiftAssignment::hasNoEmployee)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("hasEmployee");
    }

    private Constraint noWeekend(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ShiftAssignment.class)
            .filter(ShiftAssignment::isWeekend)
            .map(ShiftAssignment::hasEmployee)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("noWeekend");
    }

}
