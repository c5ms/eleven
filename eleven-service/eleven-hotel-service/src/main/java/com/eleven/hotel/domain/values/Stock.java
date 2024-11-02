package com.eleven.hotel.domain.values;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class Stock {

    private final Integer count;

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
