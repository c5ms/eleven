package com.eleven.core;

public enum SaleState {
    STARTED,
    STOPPED,
    ;

    public boolean isOnSale() {
        return this == STARTED;
    }

}
