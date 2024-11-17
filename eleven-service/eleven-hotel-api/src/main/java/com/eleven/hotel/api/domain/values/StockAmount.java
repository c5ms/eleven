package com.eleven.hotel.api.domain.values;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Embeddable
@EqualsAndHashCode
public class StockAmount {

    private Integer count;

    public static StockAmount of(Integer amount) {
        return new StockAmount(amount);
    }

    protected StockAmount() {
        this.count = 0;
    }

    private StockAmount(Integer count) {
        this.count = count;
    }

    public static StockAmount zero() {
        return new StockAmount();
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
