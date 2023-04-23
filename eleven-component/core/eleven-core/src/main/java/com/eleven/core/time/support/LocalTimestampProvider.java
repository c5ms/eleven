package com.eleven.core.time.support;

import com.eleven.core.time.TimestampProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalTimestampProvider implements TimestampProvider {

    @Override
    public long provide() {
        return System.currentTimeMillis();
    }

}
