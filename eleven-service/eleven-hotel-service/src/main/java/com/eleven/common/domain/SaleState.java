package com.eleven.common.domain;

public enum SaleState {
    STARTED,
    STOPPED,
    ;

    public boolean isOnSale() {
        return this == STARTED;
    }

}
