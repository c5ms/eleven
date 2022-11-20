package com.eleven.hotel.domain.core;

import com.eleven.hotel.api.domain.model.SaleState;

public interface Sellable {

    void startSale();

    void stopSale();

    SaleState getSaleState();

    boolean isOnSale();
}
