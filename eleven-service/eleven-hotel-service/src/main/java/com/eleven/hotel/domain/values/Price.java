package com.eleven.hotel.domain.values;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Period;

@Getter
@Embeddable
@FieldNameConstants
public class Price {

    private BigDecimal amount;

    protected Price() {
        this.amount = BigDecimal.ZERO;
    }

    public Price(BigDecimal amount) {
        this.amount = amount;
    }

    public Price(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public static Price zero() {
        return new Price();
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean greaterThan(Price price) {
        return this.amount.compareTo(price.amount) > 0;
    }

    public boolean lessThan(Price price) {
        return this.amount.compareTo(price.amount) < 0;
    }

    public boolean equalsTo(Price price) {
        return this.amount.compareTo(price.amount) == 0;
    }

    public Price multiply(DateRange stayPeriod) {
        var amount = this.amount.multiply(BigDecimal.valueOf(stayPeriod.days()));
        return new Price(amount);
    }

    public Price multiply(Period period) {
        var amount = this.amount.multiply(BigDecimal.valueOf(period.getDays()));
        return new Price(amount);
    }
}
