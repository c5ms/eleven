package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.domain.model.plan.cases.PlanSaleCase;
import com.eleven.core.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PlanTest {

    @Test
    void startSale() {
        new PlanSaleCase()
            .setSalePeriod(DateTimeRange.nextDays(LocalDateTime.now(), 5))
            .setStockAmount(StockAmount.of(100))
            .startSale();

        new PlanSaleCase()
            .setSalePeriod(DateTimeRange.empty())
            .setStockAmount(StockAmount.of(100))
            .testIsOnSale(
                new PlanSaleCase.TestIsOnSaleExpectation()
                .setExpectPlanIsOnSale(false)
            );

        new PlanSaleCase()
            .setSalePeriod(null)
            .setStockAmount(StockAmount.of(100))
            .testIsOnSale(
                new PlanSaleCase.TestIsOnSaleExpectation()
                    .setExpectPlanIsOnSale(false)
            );

        new PlanSaleCase()
            .setSalePeriod(null)
            .setPreSalePeriod(DateTimeRange.nextDays(LocalDateTime.now(), 5))
            .setStockAmount(StockAmount.of(100))
            .testIsOnSale(
                new PlanSaleCase.TestIsOnSaleExpectation()
                    .setExpectPlanIsOnSale(false)
            );

        new PlanSaleCase()
            .setPreSalePeriod(DateTimeRange.nextDays(LocalDateTime.now().plusDays(5), 5))
            .setSalePeriod(DateTimeRange.nextDays(LocalDateTime.now().plusDays(10), 5))
            .setStockAmount(StockAmount.of(100))
            .testIsOnSale(
                new PlanSaleCase.TestIsOnSaleExpectation()
                    .setExpectPlanIsOnSale(false)
            );


        new PlanSaleCase()
            .setSalePeriod(DateTimeRange.nextDays(LocalDateTime.now().plusDays(10), 5))
            .setPreSalePeriod(DateTimeRange.nextDays(LocalDateTime.now(), 5))
            .setStockAmount(StockAmount.of(100))
            .testIsOnSale(
                new PlanSaleCase.TestIsOnSaleExpectation()
                    .setExpectPlanIsOnSale(true)
            );

    }

}
