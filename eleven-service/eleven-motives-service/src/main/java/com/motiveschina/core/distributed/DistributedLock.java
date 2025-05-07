package com.motiveschina.core.distributed;


public interface DistributedLock {

    void lock(Lockable resource);

    void unlock(Lockable resource);
}
