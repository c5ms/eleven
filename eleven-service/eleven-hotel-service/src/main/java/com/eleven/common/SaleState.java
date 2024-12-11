package com.eleven.common;

public enum SaleState {
    STARTED,
    STOPPED,
    ;

    public boolean isOnSale() {
        return this == STARTED;
    }

}
