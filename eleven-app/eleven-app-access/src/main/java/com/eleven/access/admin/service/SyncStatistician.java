package com.eleven.access.admin.service;

import java.time.LocalDateTime;

public interface SyncStatistician {
    long statCount();

    long statStartedCount();

    long statRecentlyErrorCount(LocalDateTime start);
}
