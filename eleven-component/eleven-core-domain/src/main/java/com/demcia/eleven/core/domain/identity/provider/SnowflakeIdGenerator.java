package com.demcia.eleven.core.domain.identity.provider;


import com.demcia.eleven.core.domain.identity.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnowflakeIdGenerator implements IdGenerator {

    private final SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public String nextId(String schema) {
        return String.valueOf(snowflakeIdWorker.nextId());
    }
}
