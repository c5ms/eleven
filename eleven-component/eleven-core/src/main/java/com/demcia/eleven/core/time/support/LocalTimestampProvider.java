package com.demcia.eleven.core.time.support;

import com.demcia.eleven.core.time.TimestampProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalTimestampProvider implements TimestampProvider {

    @Override
    public long provide() {
        return System.currentTimeMillis();
    }

}
