package com.motiveschina.core.concurrency;


public interface DistributedLock {

    void lock(Lockable inventory) ;

    void unlock(Lockable inventory);
}
