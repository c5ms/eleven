package com.motiveschina.erp.infrastructure.application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.motiveschina.core.distributed.DistributedLock;
import com.motiveschina.core.distributed.Lockable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MockDistributedLock implements DistributedLock {

    private final Map<String, Lock> locks = new ConcurrentHashMap<>();

    @Override
    public void lock(Lockable resource) {
        var id = resource.getLockKey();
        var lock = locks.computeIfAbsent(id, aLong -> new ReentrantLock());
        lock.lock();
        log.info("lock {}", id);
    }

    @Override
    public void unlock(Lockable resource) {
        var id = resource.getLockKey();
        var lock = locks.remove(id);
        if (null != lock) {
            lock.unlock();
        }
        log.info("unlock {}", id);
    }


}
