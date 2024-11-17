package com.eleven.hotel.api.domain.enums;

public enum SaleState {
    STARTED,
    STOPPED,
    ;

    public boolean isOnSale() {
        return this == STARTED;
    }

}
