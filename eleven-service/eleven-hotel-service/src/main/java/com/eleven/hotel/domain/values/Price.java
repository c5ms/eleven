package com.eleven.hotel.domain.values;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Getter
@FieldNameConstants
public class Price {

    public static Price ZERO = Price.of(0);
    public static Price ONE = Price.of(1);

    @Column(value = "price")
    private final BigDecimal amount;

    public Price(BigDecimal amount) {
        this.amount = amount;
    }

    public static Price of(double price) {
        return of(BigDecimal.valueOf(price));
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
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
        return Price.of(amount);
    }
}
