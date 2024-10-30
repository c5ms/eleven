package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.model.SaleState;

public record PlanSummary(String id,
                          String name,
                          SaleState saleState) {
}
