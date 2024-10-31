package com.eleven.hotel.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Getter
@Embeddable
@FieldNameConstants
public class Price {

    public static Price ZERO = new Price(0);
    public static Price ONE = new Price(1);

    private BigDecimal amount;

    protected Price() {
    }

    public Price(BigDecimal amount) {
        this.amount = amount;
    }

    public Price(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean greaterTan(Price price) {
        return this.amount.compareTo(price.amount) > 0;
    }

    public boolean lessTan(Price price) {
        return this.amount.compareTo(price.amount) < 0;
    }

    public boolean equalsTo(Price price) {
        return this.amount.compareTo(price.amount) == 0;
    }

    public Price multiply(DateRange stayPeriod) {
        var amount = this.amount.multiply(BigDecimal.valueOf(stayPeriod.days()));
        return new Price(amount);
    }
}
