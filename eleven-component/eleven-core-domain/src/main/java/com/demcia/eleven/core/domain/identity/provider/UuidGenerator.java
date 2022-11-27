package com.demcia.eleven.core.domain.identity.provider;

import com.demcia.eleven.core.domain.identity.IdGenerator;

import java.util.UUID;

public class UuidGenerator implements IdGenerator {

    @Override
    public String nextId(String schema) {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
