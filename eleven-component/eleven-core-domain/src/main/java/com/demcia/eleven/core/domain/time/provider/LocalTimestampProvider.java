package com.demcia.eleven.core.domain.time.provider;

import com.demcia.eleven.core.domain.time.TimestampProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalTimestampProvider implements TimestampProvider {

    @Override
    public long provide() {
        return System.currentTimeMillis();
    }

}
