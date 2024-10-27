package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityFoundException;

public class RegisterNotFoundException extends NoEntityFoundException {

    public RegisterNotFoundException(String registerId) {
        super("not found register with id " + registerId);
    }
}
