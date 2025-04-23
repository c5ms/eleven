package com.motiveschina.core.distributed;


public interface DistributedLock {

    void lock(Lockable lockable) ;

    void unlock(Lockable lockable);
}
