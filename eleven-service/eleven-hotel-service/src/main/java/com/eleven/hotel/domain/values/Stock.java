package com.eleven.hotel.domain.values;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;


@Getter
@Embeddable
@FieldNameConstants
public class Stock {

    public static Stock ZERO = Stock.of(0);
    public static Stock ONE = Stock.of(1);

    private Integer count;

    protected Stock() {
    }

    public Stock(Integer count) {
        this.count = count;
    }

    public static Stock of(Integer amount) {
        return new Stock(amount);
    }

    public boolean isEmpty() {
        return count == null;
    }

    public boolean isZero() {
        return this.count == 0;
    }

    public boolean greaterTan(Stock stock) {
        return this.count > stock.count;
    }

    public boolean lessTan(Stock stock) {
        return this.count < stock.count;
    }

    public boolean equalsTo(Stock stock) {
        return this.count < stock.count;
    }

}
