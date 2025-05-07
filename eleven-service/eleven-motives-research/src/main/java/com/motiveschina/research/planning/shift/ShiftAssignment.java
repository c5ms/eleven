package com.motiveschina.research.planning.shift;

import lombok.Data;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

@Data
@PlanningEntity
public class ShiftAssignment {

    @PlanningId
    private int shiftId;

    private LocalDate date;

    @PlanningVariable
    private Employee employee;

    @PlanningVariable
    private IsWorkingDay isWorkingDay;

    public ShiftAssignment() {
    }

    public ShiftAssignment(int shiftId, LocalDate date) {
        this.shiftId = shiftId;
        this.date = date;
    }

    public boolean isTheSameEmployee(ShiftAssignment that) {
        return Objects.equals(that.employee, this.employee);
    }

    public boolean isTheSameDay(ShiftAssignment that) {
        return Objects.equals(that.date, this.date);
    }

    public boolean isDayOff(DayOff dayOff) {
        return this.date.equals(dayOff.getDate())
               && this.employee.equals(dayOff.getEmployee());
    }

    public boolean isWeekend() {
        return this.date.getDayOfWeek() == DayOfWeek.SATURDAY
               || this.date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public boolean isNotWeekend() {
        return !this.isWeekend();
    }

    public boolean isWorkingDay() {
        return this.isWorkingDay == IsWorkingDay.TRUE;
    }

    public boolean hasEmployee() {
        return null != this.employee;
    }

    public boolean hasNoEmployee() {
        return null == this.employee;
    }

    @Override
    public String toString() {
        return "ShiftAssignment{" +
               "shiftId=" + shiftId +
               ", employee=" + employee +
               ", isWorkingDay=" + isWorkingDay +
               ", date=" + date +
               ", day=" + date.getDayOfWeek().name().toLowerCase(Locale.ROOT) +
               '}';
    }
}

