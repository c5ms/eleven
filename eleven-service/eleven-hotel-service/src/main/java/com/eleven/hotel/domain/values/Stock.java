package com.eleven.hotel.domain.values;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@FieldNameConstants
public class Stock {

    public static Stock ZERO = Stock.of(0);
    public static Stock ONE = Stock.of(1);

    @Column("count")
    private final Integer count;

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
