package com.motiveschina.hotel.common;

public enum SaleState {
    STARTED,
    STOPPED,
    ;

    public boolean isOnSale() {
        return this == STARTED;
    }

}
