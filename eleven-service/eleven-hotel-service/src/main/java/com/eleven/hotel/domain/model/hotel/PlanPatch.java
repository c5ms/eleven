package com.eleven.hotel.domain.model.hotel;

public interface PlanPatch {
    PlanBasic getBasic();

    Integer getStock();

    com.eleven.hotel.domain.values.DateTimeRange getSalePeriod();

    com.eleven.hotel.domain.values.DateTimeRange getPreSalePeriod();

    com.eleven.hotel.domain.values.DateRange getStayPeriod();

    java.util.Set<com.eleven.hotel.api.domain.enums.SaleChannel> getChannels();
}
