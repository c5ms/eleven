package com.eleven.hotel.domain.model.plan;

import com.eleven.core.test.utils.DateRanges;
import com.eleven.core.test.utils.Domains;
import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.domain.model.plan.testcase.SalePlanTestCase;
import com.eleven.hotel.domain.values.DateRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;


class PlanTest {

    @Test
    void plan_initialize() {
        var key = PlanKey.of(1001L, 1L);
        var plan = Plan.normal()
                .hotelId(key.getHotelId())
                .stock(10)
                .preSellPeriod(null)
                .salePeriod(DateRanges.nextWeek().toDateTimeRange())
                .stayPeriod(DateRanges.nextWeek())
                .saleChannels(Set.of(SaleChannel.values()))
                .create();

        plan.setPlanId(key.getPlanId());

        // properties initialize
        Assertions.assertEquals(key, plan.toKey());
        Assertions.assertNotNull(plan.getHotelId());
        Assertions.assertNotNull(plan.getSaleType());
        Assertions.assertNotNull(plan.getStock());
        Domains.nonNullField(plan, false);
    }


    @Test
    void plan_isPreSale() {
        var plan = Plan.normal()
                .hotelId(1001L)
                .stock(10)
                .preSellPeriod(DateRanges.today().toDateTimeRange())
                .salePeriod(DateRanges.nextWeek().toDateTimeRange())
                .stayPeriod(DateRanges.nextWeek())
                .saleChannels(Set.of(SaleChannel.values()))
                .create();

        Assertions.assertTrue(plan.isPreSale());

        plan.setPreSalePeriod(DateRanges.empty());
        Assertions.assertFalse(plan.isPreSale());
    }


    @Test
    void plan_isOnSale() {
        executeIsOnSaleTest(SalePlanTestCase.builder()
                .given(SalePlanTestCase.Given.builder()
                        .preSalePeriod(DateRanges.today())
                        .salePeriod(DateRanges.nextWeek())
                        .build())
                .when(plan -> {
                    plan.startSale();
                    plan.stopSale();
                })
                .then(SalePlanTestCase.Then.builder()
                        .isOnSale(false)
                        .build())
                .build());

        executeIsOnSaleTest(
                SalePlanTestCase.builder()
                        .given(SalePlanTestCase.Given.builder()
                                .preSalePeriod(DateRanges.today())
                                .salePeriod(DateRanges.nextWeek())
                                .build())
                        .when(Plan::startSale)
                        .then(SalePlanTestCase.Then.builder()
                                .isOnSale(true)
                                .build())
                        .build()
        );

        executeIsOnSaleTest(
                SalePlanTestCase.builder()
                        .given(SalePlanTestCase.Given.builder()
                                .preSalePeriod(DateRanges.tomorrow())
                                .salePeriod(DateRanges.nextWeek())
                                .build())
                        .when(plan -> {
                            plan.startSale();
                            plan.stopSale();
                        })
                        .then(SalePlanTestCase.Then.builder()
                                .isOnSale(false)
                                .build())
                        .build()
        );


        executeIsOnSaleTest(
                SalePlanTestCase.builder()
                        .given(SalePlanTestCase.Given.builder()
                                .preSalePeriod(DateRanges.tomorrow())
                                .salePeriod(DateRanges.nextWeek())
                                .build())
                        .when(Plan::startSale)
                        .then(SalePlanTestCase.Then.builder()
                                .isOnSale(false)
                                .build())
                        .build()
        );


        executeIsOnSaleTest(
                SalePlanTestCase.builder()
                        .given(SalePlanTestCase.Given.builder()
                                .preSalePeriod(null)
                                .salePeriod(DateRanges.today())
                                .build())
                        .when(plan -> {
                            plan.startSale();
                            plan.stopSale();
                        })
                        .then(SalePlanTestCase.Then.builder()
                                .isOnSale(false)
                                .build())
                        .build()
        );

        executeIsOnSaleTest(
                SalePlanTestCase.builder()
                        .given(SalePlanTestCase.Given.builder()
                                .preSalePeriod(null)
                                .salePeriod(DateRanges.today())
                                .build())
                        .when(plan -> {
                            plan.startSale();
                            plan.stopSale();
                        })
                        .then(SalePlanTestCase.Then.builder()
                                .isOnSale(false)
                                .build())
                        .build()
        );
    }

    public void executeIsOnSaleTest(SalePlanTestCase testCase) {
        var plan = Plan.normal()
                .hotelId(1001L)
                .stock(10)
                .preSellPeriod(Optional.ofNullable(testCase.getGiven().getPreSalePeriod()).map(DateRange::toDateTimeRange).orElse(null))
                .salePeriod(testCase.getGiven().getSalePeriod().toDateTimeRange())
                .stayPeriod(DateRanges.nextWeek())
                .saleChannels(Set.of(SaleChannel.values()))
                .create();
        if (null != testCase.getWhen()) {
            testCase.getWhen().operate(plan);
        }
        Assertions.assertEquals(testCase.getThen().isOnSale(), plan.isOnSale());
    }


}
