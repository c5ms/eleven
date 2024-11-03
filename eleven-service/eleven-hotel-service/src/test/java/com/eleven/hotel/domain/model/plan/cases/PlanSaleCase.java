package com.eleven.hotel.domain.model.plan.cases;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Accessors(chain = true)
public class PlanSaleCase {

    private DateTimeRange salePeriod;
    private DateTimeRange preSalePeriod;
    private StockAmount stockAmount;

    public void startSale() {
        var hotelId = 1L;
        var roomId_1 = 1L;
        var roomId_2 = 2L;

        var plan = createPlan(hotelId);

        plan.addProduct(roomId_1, StockAmount.of(50));
        plan.setPrice(roomId_1, SaleChannel.DH, BigDecimal.valueOf(100));

        plan.addProduct(roomId_2, StockAmount.of(50));
        plan.setPrice(roomId_2, SaleChannel.DH, BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5));

        plan.startSale(roomId_1);
        Assertions.assertTrue(plan.isOnSale());
        Assertions.assertTrue(plan.isOnSale(roomId_1));
        Assertions.assertFalse(plan.isOnSale(roomId_2));

        plan.startSale(roomId_2);
        Assertions.assertTrue(plan.isOnSale());
        Assertions.assertTrue(plan.isOnSale(roomId_1));
        Assertions.assertTrue(plan.isOnSale(roomId_2));

        plan.stopSale(roomId_1);
        Assertions.assertTrue(plan.isOnSale());
        Assertions.assertFalse(plan.isOnSale(roomId_1));
        Assertions.assertTrue(plan.isOnSale(roomId_2));

        plan.stopSale(roomId_2);
        Assertions.assertFalse(plan.isOnSale());
        Assertions.assertFalse(plan.isOnSale(roomId_1));
        Assertions.assertFalse(plan.isOnSale(roomId_2));
    }

    public void testIsOnSale(TestIsOnSaleExpectation testIsOnSaleExpectation) {
        var hotelId = 1L;
        var roomId_1 = 1L;

        var plan = createPlan(hotelId);
        plan.addProduct(roomId_1, StockAmount.of(50));
        plan.setPrice(roomId_1, SaleChannel.DH, BigDecimal.valueOf(100));

        Assertions.assertFalse(plan.isOnSale());

        plan.startSale();
        Assertions.assertEquals(testIsOnSaleExpectation.expectPlanIsOnSale, plan.isOnSale());
    }

    private Plan createPlan(long hotelId) {
        return Plan.normal()
            .hotelId(hotelId)
            .saleChannels(Set.of(SaleChannel.DH))
            .salePeriod(salePeriod)
            .preSellPeriod(preSalePeriod)
            .stockAmount(stockAmount)
            .create();
    }

    @Setter
        @Accessors(chain = true)
        public static class TestIsOnSaleExpectation {
            boolean expectPlanIsOnSale;
        }
}
