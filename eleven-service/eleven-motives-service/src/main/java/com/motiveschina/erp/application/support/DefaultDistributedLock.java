package com.motiveschina.erp.application.support;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.motiveschina.core.concurrency.DistributedLock;
import com.motiveschina.core.concurrency.Lockable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
//@ConditionalOnMissingBean(DistributedLock.class)
public class DefaultDistributedLock implements DistributedLock {

    private final Map<String, Lock> locks = new ConcurrentHashMap<>();

    private final ThreadLocal<Map<String, Lock>> lockThreadLocal = ThreadLocal.withInitial(HashMap::new);

    @Override
    public void lock(Lockable lockable) {
        var id = lockable.getLockKey();
        var lock = locks.computeIfAbsent(id, aLong -> new ReentrantLock());
        lock.lock();
        lockThreadLocal.get().put(id, lock);
        log.info("lock {}",id);
    }

    @Override
    public void unlock(Lockable lockable) {
        var id = lockable.getLockKey();
        var lock = lockThreadLocal.get().get(id);
        if (null != lock) {
            lock.unlock();
        }
        log.info("unlock {}",id);
    }


}
