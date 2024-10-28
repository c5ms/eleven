package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityException;

public class RegisterNotFoundException extends NoEntityException {

    public RegisterNotFoundException(String registerId) {
        super("not found register with id " + registerId);
    }
}
