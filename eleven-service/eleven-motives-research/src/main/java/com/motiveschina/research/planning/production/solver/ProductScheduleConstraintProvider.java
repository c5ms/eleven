package com.motiveschina.research.planning.production.solver;

import com.motiveschina.research.planning.production.model.Machine;
import com.motiveschina.research.planning.production.model.ProductAssignment;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class ProductScheduleConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
            mustOnApplicableMachine(constraintFactory),
            mustOnTheSameMachineForEachProduct(constraintFactory),
            mustNoOverlap(constraintFactory),
            mustNoDelay(constraintFactory),
            isBetterToEarly(constraintFactory),
            isBetterToSaveTime(constraintFactory),
            isBetterToSaveFee(constraintFactory),
        };
    }

    private Constraint mustOnTheSameMachineForEachProduct(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ProductAssignment.class)
            .groupBy(ProductAssignment::getProduct, ConstraintCollectors.countDistinct(ProductAssignment::getMachine))
            .filter((product, integer) -> integer > 1)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("theSameProductOnTheSameMachine");
    }

    private Constraint mustOnApplicableMachine(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ProductAssignment.class)
            .filter(assignment -> !assignment.isMachineApplicable())
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("order works on applicable machine");
    }

    private Constraint mustNoOverlap(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(ProductAssignment.class)
            .filter(ProductAssignment::isTheSameMachine)
            .filter(ProductAssignment::hasOverlap)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("no duplicated order on the same machine");
    }

    private Constraint mustNoDelay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ProductAssignment.class)
            .filter(ProductAssignment::isDelay)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("noDelay");
    }

    private Constraint isBetterToSaveTime(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ProductAssignment.class)
            .penalize(HardSoftScore.ofSoft(1), ProductAssignment::getDuration)
            .asConstraint("minimizeTotalProductionTime");
    }

    private Constraint isBetterToEarly(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ProductAssignment.class)
            .penalize(HardSoftScore.ofSoft(1), ProductAssignment::getStartTime)
            .asConstraint("isBetterToEarly");
    }

    private Constraint isBetterToSaveFee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(ProductAssignment.class)
            .map(ProductAssignment::getMachine)
            .penalize(HardSoftScore.ofSoft(2), Machine::getCost)
            .asConstraint("lowFee");
    }

}
