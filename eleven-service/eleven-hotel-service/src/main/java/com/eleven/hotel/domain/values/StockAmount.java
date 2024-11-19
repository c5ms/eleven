package com.eleven.hotel.domain.values;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;


@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockAmount {

    private Integer count = 0;

    public static StockAmount zero() {
        return new StockAmount();
    }

    public static StockAmount of(Integer quantity) {
        return new StockAmount(quantity);
    }

    public boolean isEmpty() {
        return count == null;
    }

    public boolean isZero() {
        return this.count == 0;
    }

    public boolean greaterThan(StockAmount stock) {
        return this.count > stock.count;
    }

    public boolean greaterThan(int num) {
        return this.count > num;
    }

    public boolean greaterThanZero() {
        return this.count > 0;
    }

    public boolean lessThan(StockAmount stock) {
        return this.count < stock.count;
    }

    public boolean equalsTo(StockAmount stock) {
        return this.count < stock.count;
    }

    public StockAmount reduce(int num) {
        return StockAmount.of(this.count - num);
    }

}
