package com.demcia.eleven.domain.identity.provider;

import com.demcia.eleven.domain.identity.IdGenerator;

import java.util.UUID;

public class UuidGenerator implements IdGenerator {

    @Override
    public String nextId(String schema) {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
