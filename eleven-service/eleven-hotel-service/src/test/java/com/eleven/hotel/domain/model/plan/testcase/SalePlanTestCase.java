package com.eleven.hotel.domain.model.plan.testcase;

import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.values.DateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SalePlanTestCase {
    private Given given;
    private When when;
    private Then then;

    @Getter
    @Builder
    public static class Given {
        private DateRange salePeriod;
        private DateRange preSalePeriod;
    }

    @FunctionalInterface
    public interface When {

        void operate(Plan plan);

    }

    @Getter
    @Builder
    public static class Then {
        private boolean isOnSale;
    }
}
