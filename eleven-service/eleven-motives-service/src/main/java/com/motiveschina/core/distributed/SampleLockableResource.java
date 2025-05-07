package com.motiveschina.core.distributed;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class SampleLockableResource implements Lockable {

    String lockKey;

    @Override
    public String getLockKey() {
        return lockKey;
    }
}
