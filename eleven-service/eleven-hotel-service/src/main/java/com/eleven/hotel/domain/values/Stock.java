package com.eleven.hotel.domain.values;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;


@Getter
@Embeddable
@FieldNameConstants
public class Stock {

    private Integer count;

    protected Stock() {
        this.count = 0;
    }

    public Stock(Integer count) {
        this.count = count;
    }

    public static Stock of(Integer amount) {
        return new Stock(amount);
    }

    public static Stock zero() {
        return new Stock();
    }

    public boolean isEmpty() {
        return count == null;
    }

    public boolean isZero() {
        return this.count == 0;
    }

    public boolean greaterThan(Stock stock) {
        return this.count > stock.count;
    }

    public boolean greaterThanZero() {
        return this.count > 0;
    }

    public boolean lessThan(Stock stock) {
        return this.count < stock.count;
    }

    public boolean equalsTo(Stock stock) {
        return this.count < stock.count;
    }

}
